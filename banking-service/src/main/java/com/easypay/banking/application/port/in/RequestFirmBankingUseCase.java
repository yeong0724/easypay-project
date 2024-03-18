package com.easypay.banking.application.port.in;

import com.easypay.banking.domain.FirmBankingRequest;

public interface RequestFirmBankingUseCase {
    FirmBankingRequest requestFirmBanking(FirmBankingRequestCommand firmBankingRequestCommand);
}
