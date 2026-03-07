package io.github.winnpixie.pixiecraft.economy.impl;

import io.github.winnpixie.pixiecraft.economy.api.IWallet;

public class Wallet implements IWallet {
    private long balance;

    public Wallet() {
        this(0L);
    }

    public Wallet(long initialBalance) {
        this.balance = initialBalance;
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
    public boolean earn(long amount) {
        if (amount < 0L) {
            return false;
        }

        this.balance += amount;
        return true;
    }

    @Override
    public boolean spend(long amount) {
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
