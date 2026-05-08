package io.github.winnpixie.pixiecraft.hud.plugin.handlers;

import io.github.winnpixie.pixiecraft.commons.BaseEventHandler;
import io.github.winnpixie.pixiecraft.hud.plugin.PxHUDPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerConnectionHandler extends BaseEventHandler<PxHUDPlugin> {
    public PlayerConnectionHandler(PxHUDPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        getPlugin().getScoreboard().register(event.getPlayer());
    }
}
