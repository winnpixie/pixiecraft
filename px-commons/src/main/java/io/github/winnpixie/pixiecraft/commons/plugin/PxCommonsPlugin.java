package io.github.winnpixie.pixiecraft.commons.plugin;

import io.github.winnpixie.pixiecraft.commons.timers.TickTimer;
import org.bukkit.plugin.java.JavaPlugin;

public class PxCommonsPlugin extends JavaPlugin {
    private ServerTickTracker tickTracker;

    public ServerTickTracker getTickTracker() {
        return tickTracker;
    }

    @Override
    public void onEnable() {
        this.tickTracker = new ServerTickTracker();
        getServer().getScheduler().runTaskTimer(this, tickTracker, 0L, 0L);

        TickTimer.setPlugin(this);
    }
}
