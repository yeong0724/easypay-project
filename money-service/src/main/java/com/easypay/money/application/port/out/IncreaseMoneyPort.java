package com.easypay.money.application.port.out;

import com.easypay.money.adapter.out.persistence.MemberMoneyJpaEntity;
import com.easypay.money.adapter.out.persistence.MoneyChangingRequestJpaEntity;
import com.easypay.money.domain.MemberMoney;
import com.easypay.money.domain.MoneyChangingRequest;

public interface IncreaseMoneyPort {
    MoneyChangingRequestJpaEntity createMoneyChangingRequest(
            MoneyChangingRequest.TargetMembershipId targetMembershipId,
            MoneyChangingRequest.ChangingType changingType,
            MoneyChangingRequest.ChangingMoneyAmount changingMoneyAmount,
            MoneyChangingRequest.ChangingMoneyStatus changingMoneyStatus,
            MoneyChangingRequest.Uuid uuid
    );

    MemberMoneyJpaEntity increaseMoney(
            MemberMoney.MembershipId membershipId,
            int increaseMoneyAmount
    );
}
