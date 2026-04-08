package io.github.winnpixie.pixiecraft.economy.impl;

import io.github.winnpixie.pixiecraft.economy.api.IBank;
import io.github.winnpixie.pixiecraft.economy.api.IBankAccountHolder;
import io.github.winnpixie.pixiecraft.economy.api.IUser;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Bank implements IBank {
    private final String name;
    private final Map<UUID, IBankAccountHolder> holders = new ConcurrentHashMap<>();

    public Bank(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public Collection<IBankAccountHolder> getHolders() {
        return holders.values();
    }

    @Override
    public IBankAccountHolder register(IUser user) {
        IBankAccountHolder holder = new BankAccountHolder(user, this);
        holders.put(user.getId(), holder);

        return holder;
    }

    @Override
    public boolean leave(IUser user) {
        return holders.remove(user.getId()) != null;
    }

    @Override
    public IBankAccountHolder find(IUser user) {
        return holders.get(user.getId());
    }
}
