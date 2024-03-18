package com.easypay.banking.adapter.out.external.bank;

import com.easypay.banking.adapter.out.persistence.SpringDataRegisteredBankAccountRepository;
import com.easypay.banking.application.port.out.RequestBankAccountInfoPort;
import com.easypay.banking.application.port.out.RequestExternalFirmBankingPort;
import lombok.RequiredArgsConstructor;
import org.easypay.common.ExternalSystemAdapter;


@ExternalSystemAdapter
@RequiredArgsConstructor
public class BankAccountAdapter implements RequestBankAccountInfoPort, RequestExternalFirmBankingPort {
    @Override
    public BankAccount getBankAccountInfo(GetBankAccountRequest getBankAccountRequest) {
        return new BankAccount(getBankAccountRequest.getBankName(), getBankAccountRequest.getBankAccountNumber(), true);
    }

    @Override
    public FirmBankingResult requestExternalFirmBanking(ExternalFirmBankingRequest externalFirmBankingRequest) {
        /**
         * 실제 외부 은행에 http 통신을 하여 펌뱅킹 요청을 하고 결과를 응답 받는다.
         * 응답 받은 결과를 FirmBankingResult 로 Parsing 하여 return
         */
        return new FirmBankingResult(0);
    }
}
