package io.github.winnpixie.pixiecraft.economy.impl;

import io.github.winnpixie.pixiecraft.economy.model.IAccountHolder;
import io.github.winnpixie.pixiecraft.economy.model.IBank;
import io.github.winnpixie.pixiecraft.economy.model.IUser;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Bank implements IBank {
    private final String name;
    private final Map<UUID, IAccountHolder> accountHolders = new ConcurrentHashMap<>();

    public Bank(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IAccountHolder register(IUser user) {
        IAccountHolder holder = new AccountHolder();
        accountHolders.put(user.getId(), holder);

        return holder;
    }

    @Override
    public boolean leave(IUser user) {
        return accountHolders.remove(user.getId()) != null;
    }

    @Override
    public IAccountHolder find(IUser user) {
        return accountHolders.get(user.getId());
    }
}
