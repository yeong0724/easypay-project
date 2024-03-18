package com.easypay.banking.application.port.out;

import com.easypay.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import com.easypay.banking.domain.RegisteredBankAccount;

public interface RegisterBankAccountPort {
    RegisteredBankAccountJpaEntity createMembership(
            RegisteredBankAccount.MembershipId membershipId,
            RegisteredBankAccount.BankName bankName,
            RegisteredBankAccount.BankAccountNumber bankAccountNumber,
            RegisteredBankAccount.LinkedStatusIsValid linkedStatusIsValid
    );
}
