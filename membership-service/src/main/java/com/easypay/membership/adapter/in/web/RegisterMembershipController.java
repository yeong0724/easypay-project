package com.easypay.membership.adapter.in.web;

import com.easypay.membership.application.port.in.RegisterMembershipCommand;
import com.easypay.membership.application.port.in.RegisterMembershipUseCase;
import com.easypay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import com.easypay.common.WebAdapter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterMembershipController {
    private final RegisterMembershipUseCase registerMembershipUseCase;

    @PostMapping(path = "/membership/register")
    Membership registerMembership(@RequestBody RegisterMembershipRequest registerMembershipRequest) {
        // Request -> Command: Usecase 와 직접 연결된 명령어(추상화 계층으로서 중간에서 request 를 command 로 변경)
        RegisterMembershipCommand registerMembershipCommand = RegisterMembershipCommand.builder()
                .name(registerMembershipRequest.getName())
                .address(registerMembershipRequest.getAddress())
                .email(registerMembershipRequest.getEmail())
                .isValid(true)
                .isCorp(registerMembershipRequest.isCorp())
                .build();

        // Usecase
        return registerMembershipUseCase.registerMembership(registerMembershipCommand);
    }
}
