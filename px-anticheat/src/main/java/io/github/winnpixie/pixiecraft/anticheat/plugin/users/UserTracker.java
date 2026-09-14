package io.github.winnpixie.pixiecraft.anticheat.plugin.users;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserTracker {
    // TODO: Implement proper caching to prevent this from slowly eating up memory.
    private final Map<UUID, PassiveUser> passiveUsers = new HashMap<>();
    private final Map<UUID, ActiveUser> activeUsers = new ConcurrentHashMap<>();

    public PassiveUser addPassive(Player player) {
        return addPassive(player.getUniqueId());
    }

    public PassiveUser addPassive(UUID id) {
        PassiveUser user = new PassiveUser();
        passiveUsers.put(id, user);

        return user;
    }

    public PassiveUser getPassive(Player player) {
        return getPassive(player.getUniqueId());
    }

    public PassiveUser getPassive(UUID id) {
        return passiveUsers.get(id);
    }

    public ActiveUser addActive(Player player) {
        return addActive(player.getUniqueId());
    }

    public ActiveUser addActive(UUID id) {
        ActiveUser connection = new ActiveUser();
        activeUsers.put(id, connection);

        return connection;
    }

    public ActiveUser removeActive(Player player) {
        return removeActive(player.getUniqueId());
    }

    public ActiveUser removeActive(UUID id) {
        return activeUsers.remove(id);
    }

    public ActiveUser getActive(Player player) {
        return getActive(player.getUniqueId());
    }

    public ActiveUser getActive(UUID id) {
        return activeUsers.get(id);
    }
}
