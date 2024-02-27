package com.easypay.membership.application.port.out;

import com.easypay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.easypay.membership.domain.Membership;

public interface RegisterMembershipPort {
    MembershipJpaEntity createMembership(
            Membership.Name name,
            Membership.Email email,
            Membership.Address address,
            Membership.IsValid isValid,
            Membership.IsCorp isCorp
    );
}
