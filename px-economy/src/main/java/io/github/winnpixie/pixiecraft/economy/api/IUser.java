package io.github.winnpixie.pixiecraft.economy.api;

import java.util.UUID;

public interface IUser {
    UUID getId();

    IAccount getWallet();
}
