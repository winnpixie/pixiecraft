package io.github.winnpixie.pixiecraft.economy.impl;

import io.github.winnpixie.pixiecraft.economy.api.IAccount;

public class Account implements IAccount {
    private final String name;

    private long balance;

    public Account(String name) {
        this(name, 0L);
    }

    public Account(String name, long initialBalance) {
        this.name = name;
        this.balance = initialBalance;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getBalance() {
        return balance;
    }

    @Override
    public void setBalance(long balance) {
        this.balance = balance;
    }

    @Override
    public boolean deposit(long amount) {
        if (amount < 0L) {
            return false;
        }

        this.balance += amount;
        return true;
    }

    @Override
    public boolean withdraw(long amount) {
        if (amount < 0L) {
            return false;
        }

        if (balance < amount) {
            return false;
        }

        this.balance -= amount;
        return true;
    }
}
