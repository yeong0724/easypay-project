package com.easypay.membership.application.port.in;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.easypay.common.SelfValidating;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 바깥에서 안으로 들어오기 위한 인터페이스 동작을 정의하는 부분의 일부
 */
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class RegisterMembershipCommand extends SelfValidating<RegisterMembershipCommand> {
    @NotNull
    @NotBlank
    private final String name;

    @NotNull
    @NotBlank
    private final String email;

    @NotNull
    @NotBlank
    private final String address;

    @AssertTrue
    private final boolean isValid;

    private final boolean isCorp;


    public RegisterMembershipCommand(String name, String email, String address, boolean isValid, boolean isCorp) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.isValid = isValid;
        this.isCorp = isCorp;

        this.validateSelf();
    }
}
