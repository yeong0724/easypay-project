package com.easypay.banking.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "request_firm_banking")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FirmBankingRequestJpaEntity {
    @Id
    @GeneratedValue
    private Long firmBankingRequestId;

    private String fromBankName;

    private String fromBankAccountNumber;

    private String toBankName;

    private String toBankAccountNumber;

    private int moneyAmount;

    private int firmBankingStatus; // 0: 요청, 1: 완료 2: 실패

    private String uuid;

    public FirmBankingRequestJpaEntity(
            String fromBankName,
            String fromBankAccountNumber,
            String toBankName,
            String toBankAccountNumber,
            int moneyAmount,
            int firmBankingStatus,
            String uuid
    ) {
        this.fromBankName = fromBankName;
        this.fromBankAccountNumber = fromBankAccountNumber;
        this.toBankName = toBankName;
        this.toBankAccountNumber = toBankAccountNumber;
        this.moneyAmount = moneyAmount;
        this.firmBankingStatus = firmBankingStatus;
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "RequestFirmBankingJpaEntity{" +
                "firmBankingRequestId=" + firmBankingRequestId +
                ", fromBankName='" + fromBankName + '\'' +
                ", fromBankAccountNumber='" + fromBankAccountNumber + '\'' +
                ", toBankName='" + toBankName + '\'' +
                ", toBankAccountNumber='" + toBankAccountNumber + '\'' +
                ", moneyAmount=" + moneyAmount +
                ", firmBankingStatus=" + firmBankingStatus +
                '}';
    }
}
