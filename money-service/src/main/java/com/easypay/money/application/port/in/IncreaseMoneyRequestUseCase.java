package com.easypay.money.application.port.in;

import com.easypay.money.domain.MoneyChangingRequest;

public interface IncreaseMoneyRequestUseCase {
    MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand increaseMoneyRequestCommand);
}
