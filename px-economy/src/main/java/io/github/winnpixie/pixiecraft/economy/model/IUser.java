package io.github.winnpixie.pixiecraft.economy.model;

import java.util.UUID;

public interface IUser {
    UUID getId();

    IAccount getWallet();
}
