package io.github.winnpixie.pixiecraft.economy.impl;

import io.github.winnpixie.pixiecraft.economy.model.IAccount;
import io.github.winnpixie.pixiecraft.economy.model.IAccountHolder;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AccountHolder implements IAccountHolder {
    private final Map<String, IAccount> accounts = new ConcurrentHashMap<>();

    @Override
    public Collection<IAccount> getAccounts() {
        return accounts.values();
    }

    @Override
    public IAccount open(String name, long initialBalance) {
        IAccount account = new Account(name, initialBalance);
        accounts.put(name, account);

        return account;
    }

    @Override
    public boolean close(IAccount account) {
        return accounts.remove(account.getName()) != null;
    }

    @Override
    public IAccount find(String name) {
        return accounts.get(name);
    }
}
