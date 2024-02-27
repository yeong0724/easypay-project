package com.easypay.membership.application.port.in;

import com.easypay.membership.domain.Membership;

public interface FindMembershipUseCase {
    Membership findMembership(FindMembershipCommand findMembershipCommand);
}
