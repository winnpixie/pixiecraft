package io.github.winnpixie.pixiecraft.anticheat.plugin;

import io.github.winnpixie.pixiecraft.anticheat.plugin.handlers.PlayerActionHandler;
import io.github.winnpixie.pixiecraft.anticheat.plugin.handlers.PlayerConnectionHandler;
import io.github.winnpixie.pixiecraft.anticheat.plugin.users.UserTracker;
import io.github.winnpixie.pixiecraft.commons.config.ConfigurationLoader;
import org.bukkit.plugin.java.JavaPlugin;

public class PxAntiCheatPlugin extends JavaPlugin {
    private final UserTracker tracker = new UserTracker();

    private ConfigurationLoader configLoader;

    public UserTracker getTracker() {
        return tracker;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.configLoader = new ConfigurationLoader(getConfig());
        configLoader.link(CheckConfig.class);
        configLoader.load();

        new PlayerConnectionHandler(this).register();
        new PlayerActionHandler(this).register();
    }
}
