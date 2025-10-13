package io.github.winnpixie.pixiecraft.economy.model;

public interface IBank {
    String getName();

    IAccountHolder register(IUser user);

    boolean leave(IUser user);

    IAccountHolder find(IUser user);
}
