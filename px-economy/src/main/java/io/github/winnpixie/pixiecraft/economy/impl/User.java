package io.github.winnpixie.pixiecraft.economy.impl;

import io.github.winnpixie.pixiecraft.economy.model.IUser;

import java.util.UUID;

public class User implements IUser {
    private final UUID id;
    private final Account wallet = new Account("wallet");

    public User(UUID id) {
        this.id = id;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public Account getWallet() {
        return wallet;
    }
}
