package io.github.winnpixie.pixiecraft.commons.timers;

import io.github.winnpixie.pixiecraft.commons.plugin.PxCommonsPlugin;

public class TickTimer implements ITimer {
    private static PxCommonsPlugin plugin;

    private long lastTick;

    public static void setPlugin(PxCommonsPlugin plugin) {
        if (TickTimer.plugin != null) {
            throw new IllegalStateException("Plugin instance already set.");
        }

        TickTimer.plugin = plugin;
    }

    public TickTimer() {
        reset();
    }

    @Override
    public long getElapsed() {
        return plugin.getTickTracker().getCurrentTick() - lastTick;
    }

    @Override
    public void reset() {
        this.lastTick = plugin.getTickTracker().getCurrentTick();
    }
}
