package io.github.winnpixie.pixiecraft.economy.api;

public interface IAccount {
    String getName();

    long getBalance();

    void setBalance(long balance);

    boolean deposit(long amount);

    boolean withdraw(long amount);
}
