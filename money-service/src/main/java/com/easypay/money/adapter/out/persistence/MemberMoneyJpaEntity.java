package com.easypay.money.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "member_money")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberMoneyJpaEntity {
    @Id
    @GeneratedValue
    private Long memberMoneyId;

    private String membershipId;

    private int balance; // 잔액

    public MemberMoneyJpaEntity(String membershipId, int balance) {
        this.membershipId = membershipId;
        this.balance = balance;
    }
}
