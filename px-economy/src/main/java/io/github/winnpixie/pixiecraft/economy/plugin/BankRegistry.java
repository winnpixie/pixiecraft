package io.github.winnpixie.pixiecraft.economy.plugin;

import io.github.winnpixie.pixiecraft.commons.database.Database;
import io.github.winnpixie.pixiecraft.economy.api.IBank;
import io.github.winnpixie.pixiecraft.economy.api.IBankAccount;
import io.github.winnpixie.pixiecraft.economy.api.IBankAccountHolder;
import io.github.winnpixie.pixiecraft.economy.api.IUser;
import io.github.winnpixie.pixiecraft.economy.impl.Bank;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public class BankRegistry {
    private final Database<PxEconomyPlugin> database;
    private final UserManager userManager;

    private final Map<String, IBank> banks = new ConcurrentHashMap<>();

    public BankRegistry(Database<PxEconomyPlugin> database, UserManager userManager) {
        this.database = database;
        this.userManager = userManager;
    }

    public Collection<IBank> getBanks() {
        return banks.values();
    }

    public IBank open(IBank bank) {
        banks.put(bank.getName(), bank);

        return bank;
    }

    public IBank open(String name) {
        return open(new Bank(name));
    }

    public boolean remove(IBank bank) {
        return remove(bank.getName());
    }

    public boolean remove(String name) {
        return banks.remove(name) != null;
    }

    public IBank get(String name) {
        return banks.get(name);
    }

    public IBank load(String name) {
        IBank bank = get(name);
        if (bank == null) {
            bank = open(name);
        }

        final IBank effectiveBank = bank;

        try {
            database.write("CREATE TABLE IF NOT EXISTS %s_bank".formatted(name)
                    + " (owner VARCHAR(36), account VARCHAR(255), balance BIGINT, UNIQUE(owner, account))");

            database.read("SELECT owner, account, balance FROM %s_bank".formatted(name),
                    result -> {
                        while (result.next()) {
                            UUID uuid = UUID.fromString(result.getString(1));
                            IUser user = userManager.get(uuid);
                            if (user == null) {
                                user = userManager.add(uuid);
                            }

                            IBankAccountHolder holder = effectiveBank.find(user);
                            if (holder == null) {
                                holder = effectiveBank.register(user);
                            }

                            String accountName = result.getString(2);
                            IBankAccount account = holder.find(accountName);
                            if (account == null) {
                                account = holder.open(accountName);
                            }

                            long balance = result.getLong(3);
                            account.deposit(balance);
                        }
                    });
        } catch (SQLException e) {
            database.getPlugin().getLogger().log(Level.SEVERE, "Error reading bank", e);
        }

        return effectiveBank;
    }

    public void flush() {
        for (IBank bank : banks.values()) {
            save(bank);
        }
    }

    public void save(IBank bank) {
        for (IBankAccountHolder holder : bank.getHolders()) {
            try {
                database.write("DELETE FROM %s_bank WHERE (owner = ?)".formatted(bank.getName()),
                        statement ->
                                statement.setString(1, holder.getOwner().getId().toString()));
            } catch (SQLException e) {
                database.getPlugin().getLogger().log(Level.SEVERE, "Error cleaning bank", e);
            }

            for (IBankAccount account : holder.getAccounts()) {
                try {
                    database.write("INSERT INTO %s_bank(owner, account, balance) VALUES(?, ?, ?)".formatted(bank.getName())
                                    + " ON CONFLICT(owner, account) DO UPDATE SET balance = excluded.balance",
                            statement -> {
                                statement.setString(1, holder.getOwner().getId().toString());
                                statement.setString(2, account.getName());
                                statement.setLong(3, account.getBalance());
                            });
                } catch (SQLException e) {
                    database.getPlugin().getLogger().log(Level.SEVERE, "Error writing bank", e);
                }
            }
        }
    }
}
