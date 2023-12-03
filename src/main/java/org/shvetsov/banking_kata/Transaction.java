package org.shvetsov.banking_kata;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Transaction {

    private final LocalDate date;
    private final int amount;
    private final int balance;

    public Transaction(int amount, int lastBalance) {
        this.balance = lastBalance + amount;
        this.amount = amount;
        this.date = LocalDate.now();
    }
}
