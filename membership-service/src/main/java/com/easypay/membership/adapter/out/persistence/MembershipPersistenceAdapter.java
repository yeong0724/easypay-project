package com.easypay.membership.adapter.out.persistence;

import com.easypay.membership.application.port.out.FindMembershipPort;
import com.easypay.membership.application.port.out.ModifyMembershipPort;
import com.easypay.membership.application.port.out.RegisterMembershipPort;
import com.easypay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.easypay.common.PersistenceAdapter;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements RegisterMembershipPort, FindMembershipPort, ModifyMembershipPort {
    private final SpringDataMembershipRepository springDataMembershipRepository;

    @Override
    public MembershipJpaEntity createMembership(Membership.Name name, Membership.Email email, Membership.Address address, Membership.IsValid isValid, Membership.IsCorp isCorp) {
        return springDataMembershipRepository.save(
                new MembershipJpaEntity(
                        name.getName(),
                        email.getEmail(),
                        address.getAddress(),
                        isValid.isValid(),
                        isCorp.isCorp()
                )
        );
    }

    @Override
    public MembershipJpaEntity findMembership(Membership.MembershipId membershipId) {
        return springDataMembershipRepository.getById(Long.parseLong(membershipId.getMembershipId()));
    }

    @Override
    public MembershipJpaEntity modifyMembership(Membership.MembershipId membershipId, Membership.Name name, Membership.Email email, Membership.Address address, Membership.IsValid isValid, Membership.IsCorp isCorp) {
        MembershipJpaEntity membershipJpaEntity = springDataMembershipRepository.getById(Long.parseLong(membershipId.getMembershipId()));
        membershipJpaEntity.setName(name.getName());
        membershipJpaEntity.setEmail(email.getEmail());
        membershipJpaEntity.setAddress(address.getAddress());
        membershipJpaEntity.setValid(isValid.isValid());
        membershipJpaEntity.setCorp(isCorp.isCorp());

        return springDataMembershipRepository.save(membershipJpaEntity);
    }
}
