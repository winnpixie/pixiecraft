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
            database.write("CREATE TABLE IF NOT EXISTS central_bank (owner VARCHAR(36), account VARCHAR(32) PRIMARY KEY UNIQUE, balance BIGINT)");

            database.read("SELECT owner, account, balance FROM central_bank", result -> {
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
            database.getPlugin().getLogger().log(Level.SEVERE, "Error reading central_bank", e);
        }
    }

    void save(Database<PxEconomyPlugin> database) {
        for (IBankAccountHolder holder : getHolders()) {
            for (IBankAccount account : holder.getAccounts()) {
                try {
                    database.read("SELECT owner, account FROM central_bank WHERE (owner = ? AND account = ?)",
                            statement -> {
                                statement.setString(1, holder.getOwner().getId().toString());
                                statement.setString(2, account.getName());
                            },
                            result -> {
                                if (result.next()) {
                                    database.write("UPDATE central_bank SET balance = ? WHERE (owner = ? AND account = ?)",
                                            statement -> {
                                                statement.setLong(1, account.getBalance());
                                                statement.setString(2, holder.getOwner().getId().toString());
                                                statement.setString(3, account.getName());
                                            });
                                } else {
                                    database.write("INSERT INTO central_bank(owner, account, balance) VALUES(?, ?, ?)",
                                            statement -> {
                                                statement.setString(1, holder.getOwner().getId().toString());
                                                statement.setString(2, account.getName());
                                                statement.setLong(3, account.getBalance());
                                            });
                                }
                            });
                } catch (SQLException e) {
                    database.getPlugin().getLogger().log(Level.SEVERE, "Error writing central_bank", e);
                }
            }
        }
    }
}
