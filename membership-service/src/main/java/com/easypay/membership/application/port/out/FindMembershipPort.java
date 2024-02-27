package com.easypay.membership.application.port.out;

import com.easypay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.easypay.membership.domain.Membership;

public interface FindMembershipPort {
    MembershipJpaEntity findMembership(Membership.MembershipId membershipId);
}
