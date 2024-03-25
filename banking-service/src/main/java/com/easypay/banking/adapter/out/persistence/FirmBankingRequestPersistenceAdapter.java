package com.easypay.banking.adapter.out.persistence;

import com.easypay.banking.application.port.out.RequestFirmBankingPort;
import com.easypay.banking.domain.FirmBankingRequest;
import lombok.RequiredArgsConstructor;
import com.easypay.common.PersistenceAdapter;

import java.util.UUID;

@PersistenceAdapter
@RequiredArgsConstructor
public class FirmBankingRequestPersistenceAdapter implements RequestFirmBankingPort {
    private final SpringDataFirmBankingRequestRepository springDataFirmBankingRequestRepository;

    @Override
    public FirmBankingRequestJpaEntity createFirmBankingRequest(
            FirmBankingRequest.FromBankName fromBankName,
            FirmBankingRequest.FromBankAccountNumber fromBankAccountNumber,
            FirmBankingRequest.ToBankName toBankName,
            FirmBankingRequest.ToBankAccountNumber toBankAccountNumber,
            FirmBankingRequest.MoneyAmount moneyAmount,
            FirmBankingRequest.FirmBankingStatus firmBankingStatus
    ) {
        FirmBankingRequestJpaEntity firmBankingRequestJpaEntity = springDataFirmBankingRequestRepository.save(
                new FirmBankingRequestJpaEntity(
                        fromBankName.getFromBankName(),
                        fromBankAccountNumber.getFromBankAccountNumber(),
                        toBankName.getToBankName(),
                        toBankAccountNumber.getToBankAccountNumber(),
                        moneyAmount.getMoneyAmount(),
                        firmBankingStatus.getFirmBankingStatus(),
                        UUID.randomUUID().toString()
                )
        );

        return firmBankingRequestJpaEntity;
    }

    @Override
    public FirmBankingRequestJpaEntity modifyFirmBankingRequest(FirmBankingRequestJpaEntity firmBankingRequestJpaEntity) {
        return springDataFirmBankingRequestRepository.save(firmBankingRequestJpaEntity);
    }
}
