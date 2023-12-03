package org.shvetsov.banking_kata;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

/**
 * Banking Kata
 * <p><a href="https://kata-log.rocks/banking-kata">banking-kata</a>
 */
public class Account implements IAccount {

    private final List<Transaction> trans = new LinkedList<>();

    @Getter
    private int balance;
    @Override
    public void deposit(int amount) {
        trans.add(new Transaction(amount, balance));
        balance += amount;
    }

    @Override
    public void withdraw(int amount) {
        trans.add(new Transaction(-amount, balance));
        balance -= amount;
    }

    @Override
    public String printStatement() {
        StringBuilder sb = new StringBuilder();
        sb.append("Date").append("\t").append("\t").append("Amount").append("\t").append("\t").append("Balance").append("\n");
        trans.forEach(transaction -> sb.append(transaction.getDate().toString()).append("\t").append("\t")
                .append(transaction.getAmount()).append("\t").append("\t").append(transaction.getBalance()).append("\n"));
        return sb.toString();
    }

}
