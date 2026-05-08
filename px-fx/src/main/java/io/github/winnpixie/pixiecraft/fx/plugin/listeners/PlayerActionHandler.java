package io.github.winnpixie.pixiecraft.fx.plugin.listeners;

import io.github.winnpixie.pixiecraft.commons.BaseEventHandler;
import io.github.winnpixie.pixiecraft.commons.MathHelper;
import io.github.winnpixie.pixiecraft.fx.plugin.PxEffectsPlugin;
import io.github.winnpixie.pixiecraft.fx.plugin.utilities.FxConfig;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class PlayerActionHandler extends BaseEventHandler<PxEffectsPlugin> {
    public PlayerActionHandler(PxEffectsPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    private void onLevelUp(PlayerLevelChangeEvent event) {
        if (event.getNewLevel() < event.getOldLevel()) {
            return;
        }

        // TODO: Recreate Tom Clancy's The Division 2 level-up effect
        Player player = event.getPlayer();
        player.getWorld().spawnParticle(Particle.HAPPY_VILLAGER, player.getEyeLocation(), FxConfig.PARTICLE_COUNT,
                FxConfig.OFFSET_X, FxConfig.OFFSET_Y, FxConfig.OFFSET_Z);
    }

    // TODO: Fart
    @EventHandler
    private void onToggleSneak(PlayerToggleSneakEvent event) {
        if (!event.isSneaking()) {
            return;
        }

        if (MathHelper.randomDouble(0.0, 1.0) > FxConfig.FART_CHANCE) {
            return;
        }

        Player player = event.getPlayer();
        player.getWorld().spawnParticle(Particle.GUST, player.getLocation(), FxConfig.FART_PARTICLE_COUNT,
                FxConfig.FART_OFFSET_X, FxConfig.FART_OFFSET_Y, FxConfig.FART_OFFSET_Z);
    }
}
