package com.easypay.money.application.port.in;

import com.easypay.money.adapter.in.web.DecreaseMoneyChangingRequest;
import com.easypay.money.domain.MoneyChangingRequest;

public interface DecreaseMoneyRequestUseCase {
    MoneyChangingRequest decreaseMoneyRequest(DecreaseMoneyChangingRequest decreaseMoneyChangingRequest);
}
