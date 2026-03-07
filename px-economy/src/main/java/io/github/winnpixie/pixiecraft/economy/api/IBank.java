package io.github.winnpixie.pixiecraft.economy.api;

import java.util.Collection;

public interface IBank {
    String getName();

    Collection<IBankAccountHolder> getHolders();

    IBankAccountHolder register(IUser user);

    boolean leave(IUser user);

    IBankAccountHolder find(IUser user);
}
