package com.easypay.money.adapter.out.persistence;

import com.easypay.money.application.port.out.IncreaseMoneyPort;
import com.easypay.money.domain.MemberMoney;
import com.easypay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import com.easypay.common.PersistenceAdapter;

import java.sql.Timestamp;
import java.util.List;

@Log4j2
@PersistenceAdapter
@RequiredArgsConstructor
public class MoneyChangingRequestPersistenceAdapter implements IncreaseMoneyPort {
    private final SpringDataMoneyChangingRequestRepository springDataMoneyChangingRequestRepository;

    private final SpringDataMemberMoneyRepository springDataMemberMoneyRepository;

    @Override
    public MoneyChangingRequestJpaEntity createMoneyChangingRequest(
            MoneyChangingRequest.TargetMembershipId targetMembershipId,
            MoneyChangingRequest.ChangingType changingType,
            MoneyChangingRequest.ChangingMoneyAmount changingMoneyAmount,
            MoneyChangingRequest.ChangingMoneyStatus changingMoneyStatus,
            MoneyChangingRequest.Uuid uuid
    ) {
        return springDataMoneyChangingRequestRepository.save(
                new MoneyChangingRequestJpaEntity(
                        targetMembershipId.getTargetMembershipId(),
                        changingType.getChangingType(),
                        changingMoneyAmount.getChangingMoneyAmount(),
                        changingMoneyStatus.getChangingMoneyStatus(),
                        uuid.getUuid(),
                        new Timestamp(System.currentTimeMillis())
                )
        );
    }

    @Override
    public MemberMoneyJpaEntity increaseMoney(
            MemberMoney.MembershipId membershipId,
            int increaseMoneyAmount
    ) {
        // 최초 요청
        MemberMoneyJpaEntity memberMoneyJpaEntity;
        try {

            List<MemberMoneyJpaEntity> memberMoneyJpaEntityList = springDataMemberMoneyRepository.findByMembershipId(membershipId.getMembershipId());
            memberMoneyJpaEntity = memberMoneyJpaEntityList.get(0);
            memberMoneyJpaEntity.setBalance(memberMoneyJpaEntity.getBalance() + increaseMoneyAmount);
            return springDataMemberMoneyRepository.save(memberMoneyJpaEntity);
        } catch (Exception exception) {
            memberMoneyJpaEntity = new MemberMoneyJpaEntity(
                    membershipId.getMembershipId(),
                    increaseMoneyAmount
            );
            return springDataMemberMoneyRepository.save(memberMoneyJpaEntity);
        }
    }
}
