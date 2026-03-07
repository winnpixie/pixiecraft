package io.github.winnpixie.pixiecraft.economy.impl;

import io.github.winnpixie.pixiecraft.economy.api.IUser;
import io.github.winnpixie.pixiecraft.economy.api.IWallet;

import java.util.UUID;

public class User implements IUser {
    private final UUID id;
    private final IWallet wallet = new Wallet();

    public User(UUID id) {
        this.id = id;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public IWallet getWallet() {
        return wallet;
    }
}
