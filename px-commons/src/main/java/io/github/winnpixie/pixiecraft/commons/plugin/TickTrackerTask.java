package io.github.winnpixie.pixiecraft.commons.plugin;

import io.github.winnpixie.pixiecraft.commons.TickTracker;

public class TickTrackerTask implements TickTracker, Runnable {
    private final PxCommonsPlugin plugin;

    private long currentTick;

    private long lastTick;

    private double tickRate;
    private double tickTime;

    public TickTrackerTask(PxCommonsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public long getCurrentTick() {
        return currentTick;
    }

    @Override
    public double getTickRate() {
        return tickRate;
    }

    @Override
    public double getTickTime() {
        return tickTime;
    }

    @Override
    public float getTargetTickRate() {
        return plugin.getServer().getServerTickManager().getTickRate();
    }

    @Override
    public float getMaxAcceptableTickTime() {
        return 1000f / getTargetTickRate();
    }

    @Override
    public void run() {
        this.currentTick++;

        updateTickTimings();
    }

    // 1 nanosecond = 1M milliseconds
    private void updateTickTimings() {
        long now = System.nanoTime();
        long elapsedTick = (now - lastTick);

        this.tickTime = elapsedTick / 1000000.0;
        this.tickTime = Math.max(tickTime - getMaxAcceptableTickTime(), 0.0);

        this.tickRate = 1000000000.0 / Math.max(elapsedTick, 1L);
        this.tickRate = Math.min(tickRate, getTargetTickRate());

        this.lastTick = now;
    }
}
