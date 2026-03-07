package io.github.winnpixie.pixiecraft.fx.plugin.listeners;

import io.github.winnpixie.pixiecraft.commons.BaseEventHandler;
import io.github.winnpixie.pixiecraft.fx.plugin.PxEffectsPlugin;
import io.github.winnpixie.pixiecraft.fx.plugin.utilities.FxConfig;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLevelChangeEvent;

public class PlayerActionHandler extends BaseEventHandler<PxEffectsPlugin> {
    public PlayerActionHandler(PxEffectsPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    private void onLevelUp(PlayerLevelChangeEvent event) {
        if (event.getNewLevel() < event.getOldLevel()) {
            return;
        }

        Player player = event.getPlayer();

        // TODO: Recreate Tom Clancy's The Division 2 level-up effect
        player.getWorld().spawnParticle(Particle.HAPPY_VILLAGER, player.getEyeLocation(), FxConfig.PARTICLE_COUNT,
                FxConfig.OFFSET_X, FxConfig.OFFSET_Y, FxConfig.OFFSET_Z);
    }
}
