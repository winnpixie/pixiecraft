package io.github.winnpixie.pixiecraft.economy.plugin;

import io.github.winnpixie.pixiecraft.economy.impl.User;
import io.github.winnpixie.pixiecraft.economy.model.IUser;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

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
}
