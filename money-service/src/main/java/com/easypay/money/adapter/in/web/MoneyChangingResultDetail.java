package com.easypay.money.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.easypay.common.Enum.MoneyChangingResultStatus;
import com.easypay.common.Enum.EnumChangingType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyChangingResultDetail {
    private String moneyChangingRequestId;

    // 증액, 감액 구분값
    private EnumChangingType enumChangingType;

    private MoneyChangingResultStatus moneyChangingResultStatus;

    private int amount;
}


