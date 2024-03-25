package com.easypay.membership.adapter.in.web;

import com.easypay.membership.application.port.in.ModifyMembershipCommand;
import com.easypay.membership.application.port.in.ModifyMembershipUseCase;
import com.easypay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import com.easypay.common.WebAdapter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class ModifyMembershipController {
    private final ModifyMembershipUseCase modifyMembershipUseCase;

    @PostMapping(path = "/membership/modify/{membershipId}")
    ResponseEntity<Membership> modifyMembershipByMembershipId(@RequestBody ModifyMembershipRequest modifyMembershipRequest) {
        // Request -> Command: Usecase 와 직접 연결된 명령어(추상화 계층으로서 중간에서 request 를 command 로 변경)
        ModifyMembershipCommand modifyMembershipCommand = ModifyMembershipCommand.builder()
                .membershipId(modifyMembershipRequest.getMembershipId())
                .name(modifyMembershipRequest.getName())
                .address(modifyMembershipRequest.getAddress())
                .email(modifyMembershipRequest.getEmail())
                .isValid(modifyMembershipRequest.isValid())
                .isCorp(modifyMembershipRequest.isCorp())
                .build();

        // Usecase
        return ResponseEntity.ok(modifyMembershipUseCase.modifyMembership(modifyMembershipCommand));
    }
}
