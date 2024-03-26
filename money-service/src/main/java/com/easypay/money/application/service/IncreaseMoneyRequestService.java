package com.easypay.money.application.service;

import com.easypay.common.CountDownLatchManager;
import com.easypay.common.RechargingMoneyTask;
import com.easypay.common.SubTask;
import com.easypay.money.adapter.out.persistence.MemberMoneyJpaEntity;
import com.easypay.money.adapter.out.persistence.MoneyChangingRequestJpaEntity;
import com.easypay.money.adapter.out.persistence.MoneyChangingRequestMapper;
import com.easypay.money.application.port.in.IncreaseMoneyRequestCommand;
import com.easypay.money.application.port.in.IncreaseMoneyRequestUseCase;
import com.easypay.money.application.port.out.GetMembershipPort;
import com.easypay.money.application.port.out.IncreaseMoneyPort;
import com.easypay.money.application.port.out.SendRechargingMoneyTaskPort;
import com.easypay.money.domain.MemberMoney;
import com.easypay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;
import com.easypay.money.Enum.EnumChangingMoneyStatus;
import com.easypay.money.Enum.EnumChangingType;
import com.easypay.common.UseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
@Transactional
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUseCase {
    private final MoneyChangingRequestMapper moneyChangingRequestMapper;

    private final IncreaseMoneyPort increaseMoneyPort;

    private final GetMembershipPort getMembershipPort;

    private final SendRechargingMoneyTaskPort sendRechargingMoneyTaskPort;

    private final CountDownLatchManager countDownLatchManager;

    @Override
    public MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand increaseMoneyRequestCommand) {
        /**
         * > 머니의 충전 (증액)
         * 1. 고객 정보 정상유무 확인
         * 2. 고객의 연동된 계좌가 있는지 + 연동된 계좌에 잔액이 충분한지 확인
         * 3. 법인 계좌 상태도 정상인지 확인
         * 4. 증액을 위한 기록 (요청상태로 MoneyChangingRequest 생성 -> 저장)
         * 5. 펌뱅킹을 수행 (고객 연동 계좌 -> easy-pay 법인계좌)
         * 6-1. 결과가 정상적이라면 성공으로 MoneyChangingRequest 상태값 변동후 리턴
         * 6-2. 결과가 실패라면 실패로 MoneyChangingRequest 상태값 변동후 리턴
         */

        // step 1. 고객 정보 정상유무 확인
        getMembershipPort.getMembership(increaseMoneyRequestCommand.getTargetMembershipId());

        // 성공시 멤버의 MemberMoney 값 증액 처리
         MemberMoneyJpaEntity memberMoneyJpaEntity = increaseMoneyPort.increaseMoney(
                new MemberMoney.MembershipId(increaseMoneyRequestCommand.getTargetMembershipId()),
                increaseMoneyRequestCommand.getChangingMoneyAmount()
        );

         if (memberMoneyJpaEntity != null) {
             MoneyChangingRequestJpaEntity moneyChangingRequestJpaEntity = increaseMoneyPort.createMoneyChangingRequest(
                     new MoneyChangingRequest.TargetMembershipId(increaseMoneyRequestCommand.getTargetMembershipId()),
                     new MoneyChangingRequest.ChangingType(EnumChangingType.INCREASING),
                     new MoneyChangingRequest.ChangingMoneyAmount(increaseMoneyRequestCommand.getChangingMoneyAmount()),
                     new MoneyChangingRequest.ChangingMoneyStatus(EnumChangingMoneyStatus.SUCCEEDED),
                     new MoneyChangingRequest.Uuid(UUID.randomUUID().toString())
             );

             return moneyChangingRequestMapper.mapToDomainEntity(moneyChangingRequestJpaEntity);
         }

         return null;
    }

    @Override
    public MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand increaseMoneyRequestCommand) {
        /**
         * Sub-Task 란?
         * - 각 서비스에 특정 membershipId 로 Validation 검사를 수행하기 위한 Task
         */

        // 1. SubTask, Task
        SubTask validMemberTask = SubTask.builder()
                .subTaskName("validMemberTask : " + "멤버십 유효성 검사")
                .membershipId(increaseMoneyRequestCommand.getTargetMembershipId())
                .taskType("membership")
                .status("ready")
                .build();

        /**
         * 구현 되었어야 하는 Banking Sub Task
         * 1. Banking Account 유효성검사
         * 2. 요청된 금액만큼 실제 외부 FirmBanking 통신 --> 개발 환경 특성상 무조건 Ok 처리
         */
        SubTask validBankingAccountTask = SubTask.builder()
                .subTaskName("validBankingAccountTask : " + "뱅킹 계좌 유효성 검사")
                .membershipId(increaseMoneyRequestCommand.getTargetMembershipId())
                .taskType("banking")
                .status("ready")
                .build();

        List<SubTask> subTaskList = new ArrayList<>();
        subTaskList.add(validMemberTask);
        subTaskList.add(validBankingAccountTask);

        RechargingMoneyTask rechargingMoneyTask = RechargingMoneyTask.builder()
                .taskId(UUID.randomUUID().toString())
                .taskName("Increase Money Task / 머니 충전 Task")
                .subTaskList(subTaskList)
                .moneyAmount(increaseMoneyRequestCommand.getChangingMoneyAmount())
                .membershipId(increaseMoneyRequestCommand.getTargetMembershipId())
                .toBankName("BankName")
                .build();

        // step.2 - Kafka Cluster 에 Produce
        sendRechargingMoneyTaskPort.sendRechargingMoneyTaskPort(rechargingMoneyTask);
        countDownLatchManager.addCountDownLatch(rechargingMoneyTask.getTaskId());

        // step.3 - Produce 한 것에 대해 Wait
        try {
            countDownLatchManager.getCountDownLatch(rechargingMoneyTask.getTaskId()).await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }



        /**
         * step.4
         * - wait 를 하다가 task-consumer 가 Produce 해준 Task result 를 consume
         * - 받은 응답을 다시, countDownLatchManager 를 통해서 결과 데이터를 받음
         */
        String result = countDownLatchManager.getDataForKey(rechargingMoneyTask.getTaskId());
        if (result.equals("success")) {
            // 4-1. Consume ok, Logic
            MemberMoneyJpaEntity memberMoneyJpaEntity = increaseMoneyPort.increaseMoney(
                    new MemberMoney.MembershipId(increaseMoneyRequestCommand.getTargetMembershipId())
                    ,increaseMoneyRequestCommand.getChangingMoneyAmount()
            );

            if (memberMoneyJpaEntity != null) {
                return moneyChangingRequestMapper.mapToDomainEntity(increaseMoneyPort.createMoneyChangingRequest(
                                new MoneyChangingRequest.TargetMembershipId(increaseMoneyRequestCommand.getTargetMembershipId()),
                                new MoneyChangingRequest.ChangingType(EnumChangingType.INCREASING),
                                new MoneyChangingRequest.ChangingMoneyAmount(increaseMoneyRequestCommand.getChangingMoneyAmount()),
                                new MoneyChangingRequest.ChangingMoneyStatus(EnumChangingMoneyStatus.SUCCEEDED),
                                new MoneyChangingRequest.Uuid(UUID.randomUUID().toString())
                        )
                );
            }
        } else {
            // 4-2. Consume fail, Logic
            return null;
        }

        // 5. Consume Ok 처리

        return null;
    }
}
