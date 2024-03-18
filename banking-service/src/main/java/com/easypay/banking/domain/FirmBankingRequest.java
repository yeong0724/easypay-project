package com.easypay.banking.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FirmBankingRequest {
    @Getter
    private final String firmBankingRequestId;

    @Getter
    private final String fromBankName;

    @Getter
    private final String fromBankAccountNumber;

    @Getter
    private final String toBankName;

    @Getter
    private final String toBankAccountNumber;

    @Getter
    private final int moneyAmount;

    @Getter
    private final int firmBankingStatus; // 0: 요청, 1: 완료 2: 실패

    @Getter
    private final String uuid;

    public static FirmBankingRequest generateFirmBankingRequest(
            FirmBankingRequestId firmBankingRequestId,
            FromBankName fromBankName,
            FromBankAccountNumber fromBankAccountNumber,
            ToBankName toBankName,
            ToBankAccountNumber toBankAccountNumber,
            MoneyAmount moneyAmount,
            FirmBankingStatus firmBankingStatus,
            Uuid uuid
    ){
        return new FirmBankingRequest(
                firmBankingRequestId.getFirmBankingRequestId(),
                fromBankName.getFromBankName(),
                fromBankAccountNumber.getFromBankAccountNumber(),
                toBankName.getToBankName(),
                toBankAccountNumber.getToBankAccountNumber(),
                moneyAmount.getMoneyAmount(),
                firmBankingStatus.getFirmBankingStatus(),
                uuid.getUuid()
        );
    }

    @Value
    public static class FirmBankingRequestId {
        String firmBankingRequestId;

        public FirmBankingRequestId(String firmBankingRequestId) {
            this.firmBankingRequestId = firmBankingRequestId;
        }
    }

    @Value
    public static class FromBankName {
        String fromBankName;

        public FromBankName(String fromBankName) {
            this.fromBankName = fromBankName;
        }
    }

    @Value
    public static class FromBankAccountNumber {
        String fromBankAccountNumber;

        public FromBankAccountNumber(String fromBankAccountNumber) {
            this.fromBankAccountNumber = fromBankAccountNumber;
        }
    }

    @Value
    public static class ToBankName {
        String toBankName;

        public ToBankName(String toBankName) {
            this.toBankName = toBankName;
        }
    }

    @Value
    public static class ToBankAccountNumber {
        String toBankAccountNumber;

        public ToBankAccountNumber(String toBankAccountNumber) {
            this.toBankAccountNumber = toBankAccountNumber;
        }
    }

    @Value
    public static class MoneyAmount {
        int moneyAmount;

        public MoneyAmount(int moneyAmount) {
            this.moneyAmount = moneyAmount;
        }
    }

    @Value
    public static class FirmBankingStatus {
        int firmBankingStatus;

        public FirmBankingStatus(int firmBankingStatus) {
            this.firmBankingStatus = firmBankingStatus;
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
