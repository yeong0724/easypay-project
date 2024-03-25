package com.easypay.money.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberMoney {
    @Getter
    private final String memberMoneyId;

    @Getter
    private final String membershipId;

    @Getter
    private final int balance; // 잔액


    public static MemberMoney generateMoneyChangingRequest(
            MemberMoneyId memberMoneyId,
            MembershipId membershipId,
            Balance balance
    ){
        return new MemberMoney(
                memberMoneyId.getMemberMoneyId(),
                membershipId.getMembershipId(),
                balance.getBalance()
        );
    }

    @Value
    public static class MemberMoneyId {
        String memberMoneyId;

        public MemberMoneyId(String memberMoneyId) {
            this.memberMoneyId = memberMoneyId;
        }
    }

    @Value
    public static class MembershipId {
        String membershipId;

        public MembershipId(String membershipId) {
            this.membershipId = membershipId;
        }
    }

    @Value
    public static class Balance {
        int balance;

        public Balance(int balance) {
            this.balance = balance;
        }
    }
}
