package com.easypay.banking.application.service;

import com.easypay.banking.adapter.out.external.bank.BankAccount;
import com.easypay.banking.adapter.out.external.bank.GetBankAccountRequest;
import com.easypay.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import com.easypay.banking.adapter.out.persistence.RegisteredBankAccountMapper;
import com.easypay.banking.application.port.in.RegisterBankAccountCommand;
import com.easypay.banking.application.port.in.RegisterBankAccountUseCase;
import com.easypay.banking.application.port.out.GetMembershipPort;
import com.easypay.banking.application.port.out.MembershipStatus;
import com.easypay.banking.application.port.out.RegisterBankAccountPort;
import com.easypay.banking.application.port.out.RequestBankAccountInfoPort;
import com.easypay.banking.domain.RegisteredBankAccount;
import lombok.RequiredArgsConstructor;
import com.easypay.common.UseCase;
import org.springframework.transaction.annotation.Transactional;


@UseCase
@RequiredArgsConstructor
@Transactional
public class RegisterBankAccountService implements RegisterBankAccountUseCase {
    private final RegisterBankAccountPort registerBankAccountPort;

    private final RegisteredBankAccountMapper registeredBankAccountMapper;

    private final RequestBankAccountInfoPort requestBankAccountInfoPort;

    private final GetMembershipPort getMembershipPort;

    @Override
    public RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand registerBankAccountCommand) {


        /**
         * Membership Service call - 해당 멤버십 정상 여부 확인
         */
        MembershipStatus membershipStatus = getMembershipPort.getMembership(registerBankAccountCommand.getMembershipId());
        if (!membershipStatus.isValid()) {
            return null;
        }

        /**
         * 은행 계좌 등록 서비스 비즈니스 로직
         * 외부 실제 은행에 등록이 가능한 계좌인지 확인
         *  - 외부 은행에 이계좌가 정상인지 통신을 통해 확인을 한다.
         *  - Port -> Adapter -> External System 과정을 통해 통신
         * 2-1. 등록가능하면 등록한 계좌 정보 반환
         * 2-2. 등록가능하지 않다면, 에러 throws
         */
        BankAccount bankAccount = requestBankAccountInfoPort.getBankAccountInfo(new GetBankAccountRequest(
                        registerBankAccountCommand.getBankName(),
                        registerBankAccountCommand.getBankAccountNumber()
        ));

        boolean accountIsValid = bankAccount.isValid();
        if (accountIsValid) {
            RegisteredBankAccountJpaEntity registeredBankAccountJpaEntity = registerBankAccountPort.createMembership(
                    new RegisteredBankAccount.MembershipId(registerBankAccountCommand.getMembershipId()),
                    new RegisteredBankAccount.BankName(registerBankAccountCommand.getBankName()),
                    new RegisteredBankAccount.BankAccountNumber(registerBankAccountCommand.getBankAccountNumber()),
                    new RegisteredBankAccount.LinkedStatusIsValid(registerBankAccountCommand.isLinkedStatusIsValid())
            );

            // Entity -> Domain
            return registeredBankAccountMapper.mapToDomainEntity(registeredBankAccountJpaEntity);
        } else {
            return null;
        }

    }
}
