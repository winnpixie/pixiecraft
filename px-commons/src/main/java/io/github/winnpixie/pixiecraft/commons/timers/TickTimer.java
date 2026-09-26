package io.github.winnpixie.pixiecraft.commons.timers;

import io.github.winnpixie.pixiecraft.commons.plugin.PxCommonsPlugin;

public class TickTimer implements ITimer {
    private static PxCommonsPlugin plugin;

    private long tick;

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
    public boolean hasElapsed(long duration) {
        return plugin.getTickTracker().getCurrentTick() - duration > tick;
    }

    @Override
    public void reset() {
        this.tick = plugin.getTickTracker().getCurrentTick();
    }
}
