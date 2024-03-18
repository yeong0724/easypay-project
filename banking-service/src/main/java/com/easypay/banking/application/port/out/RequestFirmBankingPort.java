package com.easypay.banking.application.port.out;

import com.easypay.banking.adapter.out.persistence.FirmBankingRequestJpaEntity;
import com.easypay.banking.domain.FirmBankingRequest;

public interface RequestFirmBankingPort {
    FirmBankingRequestJpaEntity createFirmBankingRequest(
            FirmBankingRequest.FromBankName fromBankName,
            FirmBankingRequest.FromBankAccountNumber fromBankAccountNumber,
            FirmBankingRequest.ToBankName toBankName,
            FirmBankingRequest.ToBankAccountNumber toBankAccountNumber,
            FirmBankingRequest.MoneyAmount moneyAmount,
            FirmBankingRequest.FirmBankingStatus firmBankingStatus
    );

    FirmBankingRequestJpaEntity modifyFirmBankingRequest(FirmBankingRequestJpaEntity firmBankingRequestJpaEntity);
}
