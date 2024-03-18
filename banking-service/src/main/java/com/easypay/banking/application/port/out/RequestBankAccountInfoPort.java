package com.easypay.banking.application.port.out;

import com.easypay.banking.adapter.out.external.bank.BankAccount;
import com.easypay.banking.adapter.out.external.bank.GetBankAccountRequest;

public interface RequestBankAccountInfoPort {
    BankAccount getBankAccountInfo(GetBankAccountRequest getBankAccountRequest);
}
