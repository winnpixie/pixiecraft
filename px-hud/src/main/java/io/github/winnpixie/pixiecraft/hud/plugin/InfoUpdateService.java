package io.github.winnpixie.pixiecraft.hud.plugin;

import io.github.winnpixie.pixiecraft.commons.TextHelper;
import io.github.winnpixie.pixiecraft.commons.TickTracker;
import io.github.winnpixie.pixiecraft.commons.plugin.PxCommonsPlugin;
import io.github.winnpixie.pixiecraft.commons.timers.SysTimer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class InfoUpdateService implements Runnable {
    private final SysTimer timer = new SysTimer();

    private final PxHUDPlugin plugin;
    private final TickTracker tickTracker;

    private double tickRate;

    public InfoUpdateService(PxHUDPlugin plugin) {
        this.plugin = plugin;

        this.tickTracker = JavaPlugin.getPlugin(PxCommonsPlugin.class).getTickTracker();
    }

    public void register() {
        plugin.getServer().getScheduler().runTaskTimer(plugin, this, 0L, 0L);
    }

    @Override
    public void run() {
        if (timer.hasElapsed(1000L)) {
            timer.reset();

            this.tickRate = tickTracker.getTickRate();
        }

        for (Player player : plugin.getServer().getOnlinePlayers()) {
            char tickRateColor = TextHelper.getPercentColorCode(tickRate, tickTracker.getTargetTickRate());
            char tickTimeColor = TextHelper.getPercentColorCode(tickTracker.getMaxAcceptableTickTime() - tickTracker.getTickTime(),
                    tickTracker.getMaxAcceptableTickTime());

            player.setPlayerListHeaderFooter(
                    "\u00A7%c%.2f \u00A7dTPS \u00A75| \u00A7%c%.2f \u00A7dMSPT"
                            .formatted(tickRateColor, tickRate, tickTimeColor, tickTracker.getTickTime()),
                    "\u00A75IP: \u00A7d%s".formatted(HUDConfig.SERVER_IP));

            plugin.getScoreboard().update(player);
        }
    }
}
