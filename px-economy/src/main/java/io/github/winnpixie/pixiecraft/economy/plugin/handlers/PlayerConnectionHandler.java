package io.github.winnpixie.pixiecraft.economy.plugin.handlers;

import io.github.winnpixie.pixiecraft.commons.BaseEventHandler;
import io.github.winnpixie.pixiecraft.economy.api.IBankAccountHolder;
import io.github.winnpixie.pixiecraft.economy.api.IUser;
import io.github.winnpixie.pixiecraft.economy.plugin.PxEconomyPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionHandler extends BaseEventHandler<PxEconomyPlugin> {
    public PlayerConnectionHandler(PxEconomyPlugin plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        IUser user = getPlugin().getUserManager().load(player.getUniqueId());

        IBankAccountHolder holder = getPlugin().getCentralBank().register(user);
        getPlugin().getCentralBank().load(holder);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        IUser user = getPlugin().getUserManager().get(player);

        getPlugin().getUserManager().save(user);
        getPlugin().getUserManager().remove(user);

        IBankAccountHolder holder = getPlugin().getCentralBank().find(user);
        getPlugin().getCentralBank().save(holder);
        getPlugin().getCentralBank().leave(holder);
    }
}
