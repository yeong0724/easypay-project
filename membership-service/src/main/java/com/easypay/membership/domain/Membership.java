package com.easypay.membership.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

/**
 * Clean Architecture 관점에서 Membership 이라는 클래스는 접근 제어자를 private 하게 가져간다
 * - 외부로부터 오염이 되면 안되는 클래스이다.
 * - 왜냐하면 Membership 클래스는 고객정보이며, Membership Service 에 있어서 핵심 도메인이기 때문에 안전하게 관리되어야 한다.
 * - Membership 내부에서만 각 필드를 건드릴수 있다.
 * - Domain Model 성격의 클래스를 관리함에 있어서 각각의 멤버들을 private final 하게 정의하고, 각각의 value 들은 정의된 static 클래스에 의해서만 접근하고 수정할 수 있도록 만들어 두어
 *   Membership Class 는 안전하게 보호되고 안전하게 사용되어 질 수 있도록 겅제된 시스템
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Membership {

    @Getter
    private final String membershipId;

    @Getter
    private final String name;

    @Getter
    private final String email;

    @Getter
    private final String address;

    @Getter
    private final boolean isValid;

    @Getter
    private final boolean isCorp;


    /**
     * - 해당 객체가 public static class 를 포함하지 않는다면 만들어질수 없는 구조를 강제해버리기 때문에 번거럽더라도 Membership 클래스가 원치 않는 형태를 가는 상황을 미연에 방지 할 수 있다.
     * - generateMember 를 통하지 않고서는 Membership 객체를 만들수 없으므로, Membership 은 충분히 보호받고 있다고 볼 수 있다.
     */
    public static Membership generateMember(
            MembershipId membershipId,
            Name name,
            Email email,
            Address address,
            IsValid isValid,
            IsCorp isCorp
    ){
        return new Membership(membershipId.membershipId, name.name, email.email, address.address, isValid.isValid, isCorp.isCorp);
    }

    /**
     * 각각의 정보를 value 로써 관리할 수 있는 클래스들이다.
     * - Membership 에 직접적으로 접근은 불가하나 Membership 내의 MembershipId 클래스를 통해 membershipId를 지정할 수 있다.
     */
    @Value
    public static class MembershipId {
        String membershipId;

        public MembershipId(String membershipId) {
            this.membershipId = membershipId;
        }
    }

    @Value
    public static class Name {
        String name;

        public Name(String name) {
            this.name = name;
        }
    }

    @Value
    public static class Email {
        String email;

        public Email(String email) {
            this.email = email;
        }
    }

    @Value
    public static class Address {
        String address;

        public Address(String address) {
            this.address = address;
        }
    }

    @Value
    public static class IsValid {
        boolean isValid;

        public IsValid(boolean isValid) {
            this.isValid = isValid;
        }
    }

    @Value
    public static class IsCorp {
        boolean isCorp;

        public IsCorp(boolean isCorp) {
            this.isCorp = isCorp;
        }
    }
}
