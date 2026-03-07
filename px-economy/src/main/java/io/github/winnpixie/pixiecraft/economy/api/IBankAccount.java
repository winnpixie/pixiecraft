package io.github.winnpixie.pixiecraft.economy.api;

public interface IBankAccount {
    String getName();

    IBankAccountHolder getHolder();

    long getBalance();

    void setBalance(long amount);

    boolean deposit(long amount);

    boolean withdraw(long amount);
}
