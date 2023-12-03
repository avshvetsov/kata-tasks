package org.shvetsov.banking_kata;


/**
 * Banking Kata
 * <p><a href="https://kata-log.rocks/banking-kata">banking-kata</a>
 */
public interface IAccount {
    void deposit(int amount);
    void withdraw(int amount);
    String printStatement();
}