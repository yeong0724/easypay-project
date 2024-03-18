package com.easypay.banking.application.service;

import com.easypay.banking.adapter.out.external.bank.ExternalFirmBankingRequest;
import com.easypay.banking.adapter.out.external.bank.FirmBankingResult;
import com.easypay.banking.adapter.out.persistence.FirmBankingRequestJpaEntity;
import com.easypay.banking.adapter.out.persistence.FirmBankingRequestMapper;
import com.easypay.banking.application.port.in.FirmBankingRequestCommand;
import com.easypay.banking.application.port.in.RequestFirmBankingUseCase;
import com.easypay.banking.application.port.out.RequestExternalFirmBankingPort;
import com.easypay.banking.application.port.out.RequestFirmBankingPort;
import com.easypay.banking.domain.FirmBankingRequest;
import lombok.RequiredArgsConstructor;
import org.easypay.common.UseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RequestFirmBankingService implements RequestFirmBankingUseCase {
    private final RequestFirmBankingPort requestFirmBankingPort;
    private final RequestExternalFirmBankingPort requestExternalFirmBankingPort;
    private final FirmBankingRequestMapper firmBankingRequestMapper;

    @Override
    public FirmBankingRequest requestFirmBanking(FirmBankingRequestCommand firmBankingRequestCommand) {
        /**
         * Business Logic
         * 1. 요청에 대한 정보를 먼저 write
         * 2. 외부 은행에 펌뱅킹 요청
         * 3. 결과에 따라 1번에서 작성했던 FirmBankingRequest 를 update
         * 4. 결과를 return
         */

        // step_1
        FirmBankingRequestJpaEntity firmBankingRequestJpaEntity = requestFirmBankingPort.createFirmBankingRequest(
                new FirmBankingRequest.FromBankName(firmBankingRequestCommand.getFromBankName()),
                new FirmBankingRequest.FromBankAccountNumber(firmBankingRequestCommand.getFromBankAccountNumber()),
                new FirmBankingRequest.ToBankName(firmBankingRequestCommand.getToBankName()),
                new FirmBankingRequest.ToBankAccountNumber(firmBankingRequestCommand.getToBankAccountNumber()),
                new FirmBankingRequest.MoneyAmount(firmBankingRequestCommand.getMoneyAmount()),
                new FirmBankingRequest.FirmBankingStatus(0)
        );

        // step_2
        FirmBankingResult firmBankingResult = requestExternalFirmBankingPort.requestExternalFirmBanking(
                new ExternalFirmBankingRequest(
                        firmBankingRequestCommand.getFromBankName(),
                        firmBankingRequestCommand.getFromBankAccountNumber(),
                        firmBankingRequestCommand.getToBankName(),
                        firmBankingRequestCommand.getToBankAccountNumber()
                )
        );

        UUID randomUUID = UUID.randomUUID();
        firmBankingRequestJpaEntity.setUuid(randomUUID.toString());

        // step_3
        if (firmBankingResult.getResultCode() == 0) {
            // 성공
            firmBankingRequestJpaEntity.setFirmBankingStatus(1);
        } else {
            // 실패
            firmBankingRequestJpaEntity.setFirmBankingStatus(2);
        }

        // step_4: 결과를 반환하기 전에 변경된 상태값을 기준으로 save 처리
        return firmBankingRequestMapper.mapToDomainEntity(requestFirmBankingPort.modifyFirmBankingRequest(firmBankingRequestJpaEntity));
    }
}
