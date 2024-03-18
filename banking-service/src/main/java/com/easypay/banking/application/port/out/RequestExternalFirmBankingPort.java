package com.easypay.banking.application.port.out;

import com.easypay.banking.adapter.out.external.bank.ExternalFirmBankingRequest;
import com.easypay.banking.adapter.out.external.bank.FirmBankingResult;

public interface RequestExternalFirmBankingPort {
    FirmBankingResult requestExternalFirmBanking(ExternalFirmBankingRequest externalFirmBankingRequest);
}
