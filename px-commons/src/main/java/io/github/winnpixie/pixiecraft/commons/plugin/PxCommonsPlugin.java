package io.github.winnpixie.pixiecraft.commons.plugin;

import io.github.winnpixie.pixiecraft.commons.TickTracker;
import io.github.winnpixie.pixiecraft.commons.timers.TickTimer;
import org.bukkit.plugin.java.JavaPlugin;

public class PxCommonsPlugin extends JavaPlugin {
    private TickTracker tickTracker;

    public TickTracker getTickTracker() {
        return tickTracker;
    }

    @Override
    public void onEnable() {
        TickTrackerTask trackerTask = new TickTrackerTask(this);
        this.tickTracker = trackerTask;
        getServer().getScheduler().runTaskTimer(this, trackerTask, 0L, 0L);

        TickTimer.setPlugin(this);
    }
}
