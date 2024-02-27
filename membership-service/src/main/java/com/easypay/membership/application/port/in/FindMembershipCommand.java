package com.easypay.membership.application.port.in;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.easypay.common.SelfValidating;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class FindMembershipCommand extends SelfValidating<RegisterMembershipCommand> {
    private final String membershipId;
}
