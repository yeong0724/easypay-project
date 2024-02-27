package com.easypay.membership.application.service;

import com.easypay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.easypay.membership.adapter.out.persistence.MembershipMapper;
import com.easypay.membership.application.port.in.ModifyMembershipCommand;
import com.easypay.membership.application.port.in.ModifyMembershipUseCase;
import com.easypay.membership.application.port.out.ModifyMembershipPort;
import com.easypay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.easypay.common.UseCase;
import org.springframework.transaction.annotation.Transactional;

/**
 * 비즈니스 로직을 가지는 클래스로 인터페이스를 구현함으로써 비즈니스 로직을 구현한다.
 */
@UseCase
@RequiredArgsConstructor
@Transactional
public class ModifyMembershipService implements ModifyMembershipUseCase {
    private final ModifyMembershipPort modifyMembershipPort;
    private final MembershipMapper membershipMapper;

    @Override
    public Membership modifyMembership(ModifyMembershipCommand modifyMembershipCommand) {
        /**
         * - 전달받은 Command 객체를 통해 연결된 DB와 통신
         * - business logic 입장에서는 DB 또한 외부 시스템 -> port/adapter 를 통해야만 밖으로 통신 가능
         */
        MembershipJpaEntity membershipJpaEntity = modifyMembershipPort.modifyMembership(
                new Membership.MembershipId(modifyMembershipCommand.getMembershipId()),
                new Membership.Name(modifyMembershipCommand.getName()),
                new Membership.Email(modifyMembershipCommand.getEmail()),
                new Membership.Address(modifyMembershipCommand.getAddress()),
                new Membership.IsValid(modifyMembershipCommand.isValid()),
                new Membership.IsCorp(modifyMembershipCommand.isCorp())
        );

        // Entity -> Domain
        return membershipMapper.mapToDomainEntity(membershipJpaEntity);
    }
}
