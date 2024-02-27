package com.easypay.membership.adapter.out.persistence;

import com.easypay.membership.domain.Membership;
import org.springframework.stereotype.Component;

@Component
public class MembershipMapper {
    public Membership mapToDomainEntity(MembershipJpaEntity membershipJpaEntity) {
        return Membership.generateMember(
                new Membership.MembershipId(membershipJpaEntity.getMembershipId()+""),
                new Membership.Name(membershipJpaEntity.getName()),
                new Membership.Email(membershipJpaEntity.getEmail()),
                new Membership.Address(membershipJpaEntity.getAddress()),
                new Membership.IsValid(membershipJpaEntity.isValid()),
                new Membership.IsCorp(membershipJpaEntity.isCorp())
        );
    }
}
