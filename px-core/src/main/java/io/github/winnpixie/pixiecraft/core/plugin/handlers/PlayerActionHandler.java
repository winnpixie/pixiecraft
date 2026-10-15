package io.github.winnpixie.pixiecraft.core.plugin.handlers;

import io.github.winnpixie.pixiecraft.commons.BaseEventHandler;
import io.github.winnpixie.pixiecraft.core.plugin.PxCorePlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerActionHandler extends BaseEventHandler<PxCorePlugin> {
    public PlayerActionHandler(PxCorePlugin plugin) {
        super(plugin);
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        // TODO: BoK rework
    }
}
