package io.github.winnpixie.pixiecraft.hud.plugin;

import org.bukkit.entity.Player;

public class InfoUpdateService implements Runnable {
    private final PxHUDPlugin plugin;

    private long lastTick;
    private long lastDisplay;

    private double displayTps;
    private double displayMspt;

    public InfoUpdateService(PxHUDPlugin plugin) {
        this.plugin = plugin;
    }

    public void register() {
        this.lastTick = System.nanoTime();
        this.lastDisplay = System.nanoTime();

        this.displayTps = getTickRateTarget();
        this.displayMspt = 1000.0 / displayTps;

        plugin.getServer().getScheduler().runTaskTimer(plugin, this, 0L, 0L);
    }

    @Override
    public void run() {
        updateTickTimings();

        for (Player player : plugin.getServer().getOnlinePlayers()) {
            char color = getTickRateColor(displayTps);
            player.setPlayerListHeaderFooter(
                    "\u00A7%c%.2f \u00A75TPS (\u00A7%1$c%.2f \u00A75MSPT)"
                            .formatted(color, displayTps, displayMspt),
                    "\u00A75IP: \u00A7d%s".formatted(HUDConfig.SERVER_IP));

            plugin.getScoreboard().update(player);
        }
    }

    private float getTickRateTarget() {
        return plugin.getServer().getServerTickManager().getTickRate();
    }

    // 1 nanosecond = 1M milliseconds
    private void updateTickTimings() {
        long now = System.nanoTime();
        long elapsedTick = (now - lastTick);
        double tps = 1000000000.0 / Math.max(elapsedTick, 1L);

        long elapsedDisplay = (now - lastDisplay);
        if (elapsedDisplay >= 1000000000L) {
            this.displayTps = Math.min(tps, getTickRateTarget());
            this.displayMspt = elapsedTick / 1000000.0;
            this.lastDisplay = now;
        }

        this.lastTick = System.nanoTime();
    }

    private char getTickRateColor(double tickRate) {
        double percent = tickRate / getTickRateTarget();

        if (percent >= 1.0) {
            return 'a'; // Green
        } else if (percent >= 0.8) {
            return '2'; // Dark Green
        } else if (percent >= 0.6) {
            return 'e'; // Yellow
        } else if (percent >= 0.4) {
            return '6'; // Gold (Orange)
        } else if (percent >= 0.2) {
            return 'c'; // Red
        }

        return '4'; // Dark Red
    }
}
