package io.github.winnpixie.pixiecraft.economy.plugin.handlers;

import io.github.winnpixie.pixiecraft.commons.BaseEventHandler;
import io.github.winnpixie.pixiecraft.economy.model.IUser;
import io.github.winnpixie.pixiecraft.economy.plugin.PxEconomyPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerConnectionHandler extends BaseEventHandler<PxEconomyPlugin> {
    public PlayerConnectionHandler(PxEconomyPlugin plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        IUser user = getPlugin().getUserManager().get(player);
        if (user == null) {
            user = getPlugin().getUserManager().add(player);
            getPlugin().getCentralBank().register(user);
        }
    }
}
