package io.github.winnpixie.pixiecraft.economy.impl;

import io.github.winnpixie.pixiecraft.economy.api.IBank;
import io.github.winnpixie.pixiecraft.economy.api.IBankAccount;
import io.github.winnpixie.pixiecraft.economy.api.IBankAccountHolder;
import io.github.winnpixie.pixiecraft.economy.api.IUser;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BankAccountHolder implements IBankAccountHolder {
    private final IUser owner;
    private final IBank bank;
    private final Map<String, IBankAccount> accounts = new ConcurrentHashMap<>();

    public BankAccountHolder(IUser owner, IBank bank) {
        this.owner = owner;
        this.bank = bank;
    }

    @Override
    public IUser getOwner() {
        return owner;
    }

    @Override
    public IBank getBank() {
        return bank;
    }

    @Override
    public Collection<IBankAccount> getAccounts() {
        return accounts.values();
    }

    @Override
    public IBankAccount open(String name, long initialBalance) {
        IBankAccount account = new BankAccount(name, this, initialBalance);
        accounts.put(name, account);

        return account;
    }

    @Override
    public boolean close(IBankAccount account) {
        return accounts.remove(account.getName()) != null;
    }

    @Override
    public IBankAccount find(String name) {
        for (IBankAccount account : accounts.values()) {
            if (account.getName().equalsIgnoreCase(name)) {
                return account;
            }
        }

        return null;
    }
}
