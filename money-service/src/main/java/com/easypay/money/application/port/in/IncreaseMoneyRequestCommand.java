package com.easypay.money.application.port.in;

import com.easypay.money.domain.MoneyChangingRequest;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class IncreaseMoneyRequestCommand {
    @NotNull
    private final String targetMembershipId;

    @NotNull
    private final int changingMoneyAmount;

    public IncreaseMoneyRequestCommand(String targetMembershipId, int changingMoneyAmount) {
        this.targetMembershipId = targetMembershipId;
        this.changingMoneyAmount = changingMoneyAmount;
    }
}
