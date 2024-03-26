package com.easypay.money.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.easypay.money.Enum.EnumChangingMoneyStatus;
import com.easypay.money.Enum.EnumChangingType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "money_changing_request")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MoneyChangingRequestJpaEntity {
    @Id
    @GeneratedValue
    private Long moneyChangingRequestId;

    private String targetMembershipId;

    private EnumChangingType changingType;

    private int changingMoneyAmount;

    private EnumChangingMoneyStatus changingMoneyStatus;

    private String uuid;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public MoneyChangingRequestJpaEntity(
            String targetMembershipId,
            EnumChangingType changingType,
            int changingMoneyAmount,
            EnumChangingMoneyStatus changingMoneyStatus,
            String uuid,
            Timestamp timestamp
    ) {
        this.targetMembershipId = targetMembershipId;
        this.changingType = changingType;
        this.changingMoneyAmount = changingMoneyAmount;
        this.changingMoneyStatus = changingMoneyStatus;
        this.uuid = uuid;
        this.timestamp = timestamp;
    }
}
