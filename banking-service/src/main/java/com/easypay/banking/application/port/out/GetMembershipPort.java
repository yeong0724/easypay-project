package com.easypay.banking.application.port.out;

public interface GetMembershipPort {
    MembershipStatus getMembership(String membershipId);
}
