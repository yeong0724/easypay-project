package com.easypay.banking.adapter.out.persistence;

import com.easypay.banking.application.port.out.RegisterBankAccountPort;
import com.easypay.banking.domain.RegisteredBankAccount;
import lombok.RequiredArgsConstructor;
import com.easypay.common.PersistenceAdapter;

@PersistenceAdapter
@RequiredArgsConstructor
public class RegisteredBankAccountPersistenceAdapter implements RegisterBankAccountPort {
    private final SpringDataRegisteredBankAccountRepository springDataRegisteredBankAccountRepository;

    @Override
    public RegisteredBankAccountJpaEntity createMembership(
            RegisteredBankAccount.MembershipId membershipId,
            RegisteredBankAccount.BankName bankName,
            RegisteredBankAccount.BankAccountNumber bankAccountNumber,
            RegisteredBankAccount.LinkedStatusIsValid linkedStatusIsValid
    ) {
        return springDataRegisteredBankAccountRepository.save(
                new RegisteredBankAccountJpaEntity(
                        membershipId.getMembershipId(),
                        bankName.getBankName(),
                        bankAccountNumber.getBankAccountNumber(),
                        linkedStatusIsValid.isLinkedStatusIsValid()
                )
        );
    }
}
