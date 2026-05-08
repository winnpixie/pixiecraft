package io.github.winnpixie.pixiecraft.economy.impl;

import io.github.winnpixie.pixiecraft.economy.api.IBankAccount;
import io.github.winnpixie.pixiecraft.economy.api.IBankAccountHolder;

public class BankAccount implements IBankAccount {
    private final String name;
    private final IBankAccountHolder holder;

    private long balance;

    public BankAccount(String name, IBankAccountHolder holder) {
        this(name, holder, 0L);
    }

    public BankAccount(String name, IBankAccountHolder holder, long initialBalance) {
        this.name = name;
        this.holder = holder;
        this.balance = initialBalance;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IBankAccountHolder getHolder() {
        return holder;
    }

    @Override
    public long getBalance() {
        return balance;
    }

    @Override
    public void setBalance(long amount) {
        this.balance = amount;
    }

    @Override
    public boolean deposit(long amount) {
        if (amount < 0L) {
            return false;
        }

        if (balance + amount > Integer.MAX_VALUE) {
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
