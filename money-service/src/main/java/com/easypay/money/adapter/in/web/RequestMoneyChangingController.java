package com.easypay.money.adapter.in.web;

import com.easypay.money.Enum.EnumChangingType;
import com.easypay.money.Enum.MoneyChangingResultStatus;
import com.easypay.money.application.port.in.IncreaseMoneyRequestCommand;
import com.easypay.money.application.port.in.IncreaseMoneyRequestUseCase;
import com.easypay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;
import com.easypay.common.WebAdapter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestMoneyChangingController {
    private final IncreaseMoneyRequestUseCase increaseMoneyRequestUseCase;

    @PostMapping(path = "/money/increase")
    MoneyChangingRequest increaseMoneyChangingRequest(@RequestBody IncreaseMoneyChangingRequest increaseMoneyChangingRequest) {
        IncreaseMoneyRequestCommand increaseMoneyRequestCommand = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(increaseMoneyChangingRequest.getTargetMembershipId())
                .changingMoneyAmount(increaseMoneyChangingRequest.getAmount())
                .build();

        MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUseCase.increaseMoneyRequest(increaseMoneyRequestCommand);

        // MoneyChangingRequest -> MoneyChangingResultDetail 로 파싱하는 단계
        return moneyChangingRequest;
    }

    @PostMapping(path = "/money/decrease")
    MoneyChangingResultDetail decreaseMoneyChangingRequest(@RequestBody DecreaseMoneyChangingRequest decreaseMoneyChangingRequest) {

        // MoneyChangingRequest -> MoneyChangingResultDetail 로 파싱하는 단계

        // return decreaseMoneyRequestUseCase.decreaseMoneyRequest()
        return null;
    }

    @PostMapping(path = "/money/increase-async")
    MoneyChangingResultDetail increaseMoneyChangingRequestAsync(@RequestBody IncreaseMoneyChangingRequest request) {
        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .changingMoneyAmount(request.getAmount())
                .build();

        MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUseCase.increaseMoneyRequestAsync(command);

        // MoneyChangingRequest -> MoneyChangingResultDetail
        MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail(
                moneyChangingRequest.getMoneyChangingRequestId(),
                EnumChangingType.INCREASING,
                MoneyChangingResultStatus.SUCCEEDED,
                moneyChangingRequest.getChangingMoneyAmount());
        return resultDetail;
    }
}
