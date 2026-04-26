package io.github.winnpixie.pixiecraft.agriculture.plugin.handlers;

import io.github.winnpixie.pixiecraft.agriculture.plugin.PxAgriculturePlugin;
import io.github.winnpixie.pixiecraft.commons.BaseEventHandler;
import io.github.winnpixie.pixiecraft.commons.PDCWrapper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerConnectionHandler extends BaseEventHandler<PxAgriculturePlugin> {
    public PlayerConnectionHandler(PxAgriculturePlugin plugin) {
        super(plugin);
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        PDCWrapper<PxAgriculturePlugin> pdc = new PDCWrapper<>(getPlugin(), event.getPlayer());
        if (!pdc.has("axe_stripping")) {
            pdc.setBoolean("axe_stripping", true);
        }
    }
}
