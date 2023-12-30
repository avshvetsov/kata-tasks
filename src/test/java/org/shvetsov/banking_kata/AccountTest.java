package org.shvetsov.banking_kata;

import org.junit.jupiter.api.Test;
import org.shvetsov.banking_kata.Account;

import static org.assertj.core.api.Assertions.assertThat;

class AccountTest {

    @Test
    public void depositWithdrawalTest() {
        Account acc = new Account();
        acc.deposit(100);
        acc.withdraw(50);
        acc.deposit(150);

        assertThat(acc.getBalance()).isEqualTo(200);
        System.out.println(acc.printStatement());
    }
}