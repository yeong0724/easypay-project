package com.easypay.money.application.service;

import com.easypay.money.adapter.out.persistence.MemberMoneyJpaEntity;
import com.easypay.money.adapter.out.persistence.MoneyChangingRequestJpaEntity;
import com.easypay.money.adapter.out.persistence.MoneyChangingRequestMapper;
import com.easypay.money.application.port.in.IncreaseMoneyRequestCommand;
import com.easypay.money.application.port.in.IncreaseMoneyRequestUseCase;
import com.easypay.money.application.port.out.IncreaseMoneyPort;
import com.easypay.money.domain.MemberMoney;
import com.easypay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;
import com.easypay.common.Enum.EnumChangingMoneyStatus;
import com.easypay.common.Enum.EnumChangingType;
import com.easypay.common.UseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
@Transactional
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUseCase {
    private final MoneyChangingRequestMapper moneyChangingRequestMapper;

    private final IncreaseMoneyPort increaseMoneyPort;

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

        // step.1 ~ 5 까지는 추후 IPC 통신 파트 개발후 이어서 개발

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
}
