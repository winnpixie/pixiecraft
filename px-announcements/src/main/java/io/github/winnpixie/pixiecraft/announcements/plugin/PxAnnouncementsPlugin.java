package io.github.winnpixie.pixiecraft.announcements.plugin;

import io.github.winnpixie.pixiecraft.commons.config.ConfigurationLoader;
import org.bukkit.plugin.java.JavaPlugin;

public class PxAnnouncementsPlugin extends JavaPlugin {
    private ConfigurationLoader configLoader;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.configLoader = new ConfigurationLoader(getConfig());
        configLoader.link(AnnouncerConfig.class);
        configLoader.load();

        getServer().getScheduler().runTaskTimer(this, new BroadcastTask(this),
                0L, (long) (AnnouncerConfig.INTERVAL * 60L * 20L));
    }
}
