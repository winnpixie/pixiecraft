package io.github.winnpixie.pixiecraft.economy.plugin;

import io.github.winnpixie.pixiecraft.commons.database.Database;
import io.github.winnpixie.pixiecraft.economy.api.IBankAccount;
import io.github.winnpixie.pixiecraft.economy.api.IBankAccountHolder;
import io.github.winnpixie.pixiecraft.economy.api.IUser;
import io.github.winnpixie.pixiecraft.economy.impl.Bank;

import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;

public class CentralBank extends Bank {
    public CentralBank() {
        super("Central Bank");
    }

    void load(Database<PxEconomyPlugin> database) {
        try {
            database.write("CREATE TABLE IF NOT EXISTS bank"
                    + " (owner VARCHAR(36), account VARCHAR(255), balance BIGINT, UNIQUE(owner, account))");

            database.read("SELECT owner, account, balance FROM bank", result -> {
                while (result.next()) {
                    UUID uuid = UUID.fromString(result.getString(1));
                    IUser user = database.getPlugin().getUserManager().get(uuid);
                    if (user == null) {
                        user = database.getPlugin().getUserManager().add(uuid);
                    }

                    IBankAccountHolder holder = find(user);
                    if (holder == null) {
                        holder = register(user);
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
    }

    void save(Database<PxEconomyPlugin> database) {
        for (IBankAccountHolder holder : getHolders()) {
            for (IBankAccount account : holder.getAccounts()) {
                try {
                    database.write("INSERT INTO bank(owner, account, balance) VALUES(?, ?, ?)"
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
