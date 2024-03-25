package com.easypay.membership.application.service;

import com.easypay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.easypay.membership.adapter.out.persistence.MembershipMapper;
import com.easypay.membership.application.port.in.FindMembershipCommand;
import com.easypay.membership.application.port.in.FindMembershipUseCase;
import com.easypay.membership.application.port.out.FindMembershipPort;
import com.easypay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import com.easypay.common.UseCase;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@UseCase
@Transactional
public class FindMembershipService implements FindMembershipUseCase {
    private final FindMembershipPort findMembershipPort;
    private final MembershipMapper membershipMapper;

    @Override
    public Membership findMembership(FindMembershipCommand findMembershipCommand) {
        MembershipJpaEntity membershipJpaEntity = findMembershipPort.findMembership(new Membership.MembershipId(findMembershipCommand.getMembershipId()));
        return membershipMapper.mapToDomainEntity(membershipJpaEntity);
    }
}
