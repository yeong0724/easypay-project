package com.easypay.banking.adapter.out.persistence;

import com.easypay.banking.domain.FirmBankingRequest;
import org.springframework.stereotype.Component;

@Component
public class FirmBankingRequestMapper {
    public FirmBankingRequest mapToDomainEntity(FirmBankingRequestJpaEntity firmBankingRequestJpaEntity) {
        return FirmBankingRequest.generateFirmBankingRequest(
                new FirmBankingRequest.FirmBankingRequestId(firmBankingRequestJpaEntity.getFirmBankingRequestId()+""),
                new FirmBankingRequest.FromBankName(firmBankingRequestJpaEntity.getFromBankName()),
                new FirmBankingRequest.FromBankAccountNumber(firmBankingRequestJpaEntity.getFromBankAccountNumber()),
                new FirmBankingRequest.ToBankName(firmBankingRequestJpaEntity.getToBankName()),
                new FirmBankingRequest.ToBankAccountNumber(firmBankingRequestJpaEntity.getToBankAccountNumber()),
                new FirmBankingRequest.MoneyAmount(firmBankingRequestJpaEntity.getMoneyAmount()),
                new FirmBankingRequest.FirmBankingStatus(firmBankingRequestJpaEntity.getFirmBankingStatus()),
                new FirmBankingRequest.Uuid(firmBankingRequestJpaEntity.getUuid())
        );
    }
}
