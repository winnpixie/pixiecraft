package io.github.winnpixie.pixiecraft.economy.model;

import java.util.Collection;

public interface IAccountHolder {
    Collection<IAccount> getAccounts();

    default IAccount open(String name) {
        return open(name, 0L);
    }

    IAccount open(String name, long initialBalance);

    boolean close(IAccount account);

    IAccount find(String name);
}
