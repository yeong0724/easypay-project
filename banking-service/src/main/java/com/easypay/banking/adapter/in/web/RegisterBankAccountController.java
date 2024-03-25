package com.easypay.banking.adapter.in.web;

import com.easypay.banking.application.port.in.RegisterBankAccountCommand;
import com.easypay.banking.application.port.in.RegisterBankAccountUseCase;
import com.easypay.banking.domain.RegisteredBankAccount;
import lombok.RequiredArgsConstructor;
import com.easypay.common.WebAdapter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterBankAccountController {
    private final RegisterBankAccountUseCase registerBankAccountUseCase;

    @PostMapping(path = "/banking/account/register")
    RegisteredBankAccount registerBankAccount(@RequestBody RegisterBankAccountRequest registerBankAccountRequest) {
        RegisterBankAccountCommand registerBankAccountCommand = RegisterBankAccountCommand.builder()
                .membershipId(registerBankAccountRequest.getMembershipId())
                .bankName(registerBankAccountRequest.getBankName())
                .bankAccountNumber(registerBankAccountRequest.getBankAccountNumber())
                .linkedStatusIsValid(registerBankAccountRequest.isLinkedStatusIsValid())
                .build();

        // use-case

        RegisteredBankAccount registeredBankAccount = registerBankAccountUseCase.registerBankAccount(registerBankAccountCommand);
        if (registeredBankAccount == null) {
            // ToDo: Error Handling
            return null;
        }
        return registeredBankAccount;
    }
}
