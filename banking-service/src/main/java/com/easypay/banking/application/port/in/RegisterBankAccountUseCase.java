package com.easypay.banking.application.port.in;

import com.easypay.banking.domain.RegisteredBankAccount;

public interface RegisterBankAccountUseCase {
    RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand registerBankAccountCommand);
}
