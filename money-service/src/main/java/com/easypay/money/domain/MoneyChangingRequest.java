package com.easypay.money.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import com.easypay.common.Enum.EnumChangingMoneyStatus;
import com.easypay.common.Enum.EnumChangingType;

import java.util.Date;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MoneyChangingRequest {
    @Getter
    private final String moneyChangingRequestId;

    // 어떤 고객의 증액/감액 요청을 했는지에 대한 멤버 정보
    @Getter
    private final String targetMembershipId;

    // 증액 or 감액 요청 구분 (Enum)
    @Getter
    private final EnumChangingType changingType; // 0: 증액, 1: 감액

    // 증액, 감액에 대한 금액
    @Getter
    private final int changingMoneyAmount;

    // 머니 변액 요청에 대한 상태값
    @Getter
    private final EnumChangingMoneyStatus changingMoneyStatus;

    @Getter
    private final String uuid;

    @Getter
    private final Date createdAt;

    public static MoneyChangingRequest generateMoneyChangingRequest(
            MoneyChangingRequestId moneyChangingRequestId,
            TargetMembershipId targetMembershipId,
            ChangingType changingType,
            ChangingMoneyAmount changingMoneyAmount,
            ChangingMoneyStatus changingMoneyStatus,
            Uuid uuid
    ){
        return new MoneyChangingRequest(
                moneyChangingRequestId.getMoneyChangingRequestId(),
                targetMembershipId.getTargetMembershipId(),
                changingType.getChangingType(),
                changingMoneyAmount.getChangingMoneyAmount(),
                changingMoneyStatus.getChangingMoneyStatus(),
                uuid.getUuid(),
                new Date()
        );
    }

    @Value
    public static class MoneyChangingRequestId {
        String moneyChangingRequestId;

        public MoneyChangingRequestId(String moneyChangingRequestId) {
            this.moneyChangingRequestId = moneyChangingRequestId;
        }
    }

    @Value
    public static class TargetMembershipId {
        String targetMembershipId;

        public TargetMembershipId(String targetMembershipId) {
            this.targetMembershipId = targetMembershipId;
        }
    }

    @Value
    public static class ChangingType {
        EnumChangingType changingType;

        public ChangingType(EnumChangingType changingType) {
            this.changingType = changingType;
        }
    }
    @Value
    public static class ChangingMoneyAmount {
        int changingMoneyAmount;

        public ChangingMoneyAmount(int changingMoneyAmount) {
            this.changingMoneyAmount = changingMoneyAmount;
        }
    }

    @Value
    public static class ChangingMoneyStatus {
        EnumChangingMoneyStatus changingMoneyStatus;

        public ChangingMoneyStatus(EnumChangingMoneyStatus changingMoneyStatus) {
            this.changingMoneyStatus = changingMoneyStatus;
        }
    }

    @Value
    public static class Uuid {
        String uuid;

        public Uuid(String uuid) {
            this.uuid = uuid;
        }
    }
}
