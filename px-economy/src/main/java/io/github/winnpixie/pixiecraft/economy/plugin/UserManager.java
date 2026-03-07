package io.github.winnpixie.pixiecraft.economy.plugin;

import io.github.winnpixie.pixiecraft.commons.database.Database;
import io.github.winnpixie.pixiecraft.economy.api.IUser;
import io.github.winnpixie.pixiecraft.economy.impl.User;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public class UserManager {
    private final Map<UUID, IUser> users = new ConcurrentHashMap<>();

    public IUser add(Player player) {
        return add(player.getUniqueId());
    }

    public IUser add(UUID id) {
        IUser user = new User(id);
        users.put(id, user);

        return user;
    }

    public boolean remove(Player player) {
        return remove(player.getUniqueId());
    }

    public boolean remove(IUser user) {
        return remove(user.getId());
    }

    public boolean remove(UUID id) {
        return users.remove(id) != null;
    }

    public IUser get(Player player) {
        return get(player.getUniqueId());
    }

    public IUser get(UUID id) {
        return users.get(id);
    }

    public Map<UUID, IUser> getUsers() {
        return users;
    }

    void loadUsers(Database<PxEconomyPlugin> database) {
        try {
            database.write("CREATE TABLE IF NOT EXISTS wallets (id VARCHAR(36) PRIMARY KEY UNIQUE, balance BIGINT)");

            database.read("SELECT id, balance FROM wallets", result -> {
                while (result.next()) {
                    UUID uuid = UUID.fromString(result.getString(1));
                    IUser user = get(uuid);
                    if (user == null) {
                        user = add(uuid);
                    }

                    long balance = result.getLong(2);
                    user.getWallet().setBalance(balance);
                }
            });
        } catch (SQLException e) {
            database.getPlugin().getLogger().log(Level.WARNING, "Error reading wallets", e);
        }
    }

    void saveUsers(Database<PxEconomyPlugin> database) {
        for (IUser user : users.values()) {
            try {
                database.read("SELECT id FROM wallets WHERE (id = ?)",
                        statement -> statement.setString(1, user.getId().toString()),
                        result -> {
                            if (result.next()) {
                                database.write("UPDATE wallets SET balance = ? WHERE id = ?",
                                        statement -> {
                                            statement.setLong(1, user.getWallet().getBalance());
                                            statement.setString(2, user.getId().toString());
                                        });
                            } else {
                                database.write("INSERT INTO wallets(id, balance) VALUES(?, ?)",
                                        statement -> {
                                            statement.setString(1, user.getId().toString());
                                            statement.setLong(2, user.getWallet().getBalance());
                                        });
                            }
                        });
            } catch (SQLException e) {
                database.getPlugin().getLogger().log(Level.WARNING, "Error writing wallets", e);
            }
        }
    }
}
