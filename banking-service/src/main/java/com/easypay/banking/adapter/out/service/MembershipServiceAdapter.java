package com.easypay.banking.adapter.out.service;

import com.easypay.banking.application.port.out.GetMembershipPort;
import com.easypay.banking.application.port.out.MembershipStatus;
import com.easypay.common.CommonHttpClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MembershipServiceAdapter implements GetMembershipPort {
    private final CommonHttpClient commonHttpClient;

    private final String membershipServiceUrl;

    public MembershipServiceAdapter(
            CommonHttpClient commonHttpClient,
            @Value("${service.membership.url}") String membershipServiceUrl
    ) {
        this.commonHttpClient = commonHttpClient;
        this.membershipServiceUrl = membershipServiceUrl;
    }

    @Override
    public MembershipStatus getMembership(String membershipId) {
        // 구현된 http Client 를 통해서 Membership-service 와 http 통신
        String url = String.join("/", membershipServiceUrl, "membership", membershipId);
        try {
            // Json 형태의 Membership
            String jsonResponse = commonHttpClient.sendGetRequest(url).body();
            ObjectMapper objectMapper = new ObjectMapper();
            Membership membership = objectMapper.readValue(jsonResponse, Membership.class);

            if (membership.isValid()) {
                return new MembershipStatus(membership.getMembershipId(), true);
            } else {
                return new MembershipStatus(membership.getMembershipId(), false);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
