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
    private final Database<PxEconomyPlugin> database;

    private final Map<UUID, IUser> users = new ConcurrentHashMap<>();

    public UserManager(Database<PxEconomyPlugin> database) {
        this.database = database;
    }

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

    public IUser load(UUID id) {
        IUser user = add(id);

        try {
            database.write("CREATE TABLE IF NOT EXISTS wallets"
                    + " (id VARCHAR(36) PRIMARY KEY, balance BIGINT)");

            database.read("SELECT id, balance FROM wallets WHERE (id = ?)",
                    statement -> statement.setString(1, id.toString()),
                    result -> {
                        while (result.next()) {
                            UUID uuid = UUID.fromString(result.getString(1));
                            if (!uuid.equals(id)) {
                                return;
                            }

                            long balance = result.getLong(2);
                            user.getWallet().setBalance(balance);
                        }
                    });
        } catch (SQLException e) {
            database.getPlugin().getLogger().log(Level.WARNING, "Error reading wallet", e);
        }

        return user;
    }

    public void flush() {
        for (IUser user : users.values()) {
            save(user);
        }
    }

    public void save(IUser user) {
        try {
            database.write("CREATE TABLE IF NOT EXISTS wallets"
                    + " (id VARCHAR(36) PRIMARY KEY, balance BIGINT)");

            database.write("INSERT INTO wallets(id, balance) VALUES(?, ?)"
                            + " ON CONFLICT(id) DO UPDATE SET balance = excluded.balance",
                    statement -> {
                        statement.setString(1, user.getId().toString());
                        statement.setLong(2, user.getWallet().getBalance());
                    });
        } catch (SQLException e) {
            database.getPlugin().getLogger().log(Level.WARNING, "Error writing wallet", e);
        }
    }
}
