package io.github.winnpixie.pixiecraft.economy.api;

import java.util.Collection;

public interface IBankAccountHolder {
    IUser getOwner();

    IBank getBank();

    Collection<IBankAccount> getAccounts();

    default IBankAccount open(String name) {
        return open(name, 0L);
    }

    IBankAccount open(String name, long initialBalance);

    boolean close(IBankAccount account);

    IBankAccount find(String name);
}
