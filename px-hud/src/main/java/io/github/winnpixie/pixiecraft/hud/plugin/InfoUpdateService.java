package io.github.winnpixie.pixiecraft.hud.plugin;

import io.github.winnpixie.pixiecraft.commons.TextHelper;
import org.bukkit.entity.Player;

public class InfoUpdateService implements Runnable {
    private final PxHUDPlugin plugin;

    private long lastTick;
    private long lastDisplay;

    private double tickRate;
    private double tickTime;

    public InfoUpdateService(PxHUDPlugin plugin) {
        this.plugin = plugin;
    }

    public void register() {
        this.lastTick = System.nanoTime();
        this.lastDisplay = System.nanoTime();

        this.tickRate = getTickRateTarget();
        this.tickTime = getMaxAcceptableTickTime();

        plugin.getServer().getScheduler().runTaskTimer(plugin, this, 0L, 0L);
    }

    @Override
    public void run() {
        updateTickTimings();

        for (Player player : plugin.getServer().getOnlinePlayers()) {
            char tickRateColor = TextHelper.getPercentColorCode(tickRate, getTickRateTarget());
            char tickTimeColor = TextHelper.getPercentColorCode(getMaxAcceptableTickTime() - tickTime, getMaxAcceptableTickTime());

            player.setPlayerListHeaderFooter(
                    "\u00A7%c%.2f \u00A7dTPS \u00A75| \u00A7%c%.2f \u00A7dMSPT"
                            .formatted(tickRateColor, tickRate, tickTimeColor, tickTime),
                    "\u00A75IP: \u00A7d%s".formatted(HUDConfig.SERVER_IP));

            plugin.getScoreboard().update(player);
        }
    }

    private float getTickRateTarget() {
        return plugin.getServer().getServerTickManager().getTickRate();
    }

    private float getMaxAcceptableTickTime() {
        return 1000f / getTickRateTarget();
    }

    // 1 nanosecond = 1M milliseconds
    private void updateTickTimings() {
        long now = System.nanoTime();
        long elapsedTick = (now - lastTick);
        this.tickTime = elapsedTick / 1000000.0;
        this.tickTime = Math.max(tickTime - getMaxAcceptableTickTime(), 0.0);

        long elapsedDisplay = (now - lastDisplay);
        if (elapsedDisplay >= 1000000000L) {
            this.tickRate = 1000000000.0 / Math.max(elapsedTick, 1L);
            this.tickRate = Math.min(tickRate, getTickRateTarget());

            this.lastDisplay = now;
        }

        this.lastTick = now;
    }
}
