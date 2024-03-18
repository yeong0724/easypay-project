package com.easypay.banking.adapter.in.web;

import com.easypay.banking.application.port.in.FirmBankingRequestCommand;
import com.easypay.banking.application.port.in.RegisterBankAccountCommand;
import com.easypay.banking.application.port.in.RequestFirmBankingUseCase;
import com.easypay.banking.domain.FirmBankingRequest;
import com.easypay.banking.domain.RegisteredBankAccount;
import lombok.RequiredArgsConstructor;
import org.easypay.common.WebAdapter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestFirmBankingController {
    private final RequestFirmBankingUseCase requestFirmBankingUseCase;

    @PostMapping(path = "/banking/firmBanking/request")
    FirmBankingRequest requestFirmBanking(@RequestBody RequestFirmBankingRequest requestFirmBankingRequest) {
        FirmBankingRequestCommand firmBankingRequestCommand = FirmBankingRequestCommand.builder()
                .fromBankName(requestFirmBankingRequest.getFromBankName())
                .fromBankAccountNumber(requestFirmBankingRequest.getFromBankAccountNumber())
                .toBankName(requestFirmBankingRequest.getToBankName())
                .toBankAccountNumber(requestFirmBankingRequest.getToBankAccountNumber())
                .moneyAmount(requestFirmBankingRequest.getMoneyAmount())
                .build();

        return requestFirmBankingUseCase.requestFirmBanking(firmBankingRequestCommand);
    }
}
