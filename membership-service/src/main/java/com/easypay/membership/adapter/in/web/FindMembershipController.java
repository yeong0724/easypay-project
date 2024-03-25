package com.easypay.membership.adapter.in.web;

import com.easypay.membership.application.port.in.FindMembershipCommand;
import com.easypay.membership.application.port.in.FindMembershipUseCase;
import com.easypay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import com.easypay.common.WebAdapter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class FindMembershipController {
    private final FindMembershipUseCase findMembershipUseCase;

    @GetMapping(path = "/membership/{membershipId}")
    ResponseEntity<Membership> findMembershipByMembershipId(@PathVariable String membershipId) {
        // Request -> Command: Usecase 와 직접 연결된 명령어(추상화 계층으로서 중간에서 request 를 command 로 변경)
        FindMembershipCommand findMembershipCommand = FindMembershipCommand.builder()
                .membershipId(membershipId).build();

        // Usecase
        return ResponseEntity.ok(findMembershipUseCase.findMembership(findMembershipCommand));
    }
}
