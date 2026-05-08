package io.github.winnpixie.pixiecraft.hud.plugin;

import io.github.winnpixie.pixiecraft.commons.config.ConfigurationLoader;
import io.github.winnpixie.pixiecraft.hud.plugin.handlers.PlayerConnectionHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class PxHUDPlugin extends JavaPlugin {
    private final CustomScoreboard scoreboard = new CustomScoreboard(this);

    private ConfigurationLoader configLoader;

    public CustomScoreboard getScoreboard() {
        return scoreboard;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.configLoader = new ConfigurationLoader(getConfig());
        configLoader.link(HUDConfig.class);
        configLoader.load();

        new PlayerConnectionHandler(this).register();

        new InfoUpdateService(this).register();
    }
}
