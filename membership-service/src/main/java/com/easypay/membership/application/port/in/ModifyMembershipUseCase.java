package com.easypay.membership.application.port.in;

import com.easypay.membership.domain.Membership;

public interface ModifyMembershipUseCase {
    Membership modifyMembership(ModifyMembershipCommand modifyMembershipCommand);
}
