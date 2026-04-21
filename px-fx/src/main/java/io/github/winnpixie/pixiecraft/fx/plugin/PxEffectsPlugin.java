package io.github.winnpixie.pixiecraft.fx.plugin;

import io.github.winnpixie.pixiecraft.commons.config.ConfigurationLoader;
import io.github.winnpixie.pixiecraft.fx.plugin.listeners.EntityActionHandler;
import io.github.winnpixie.pixiecraft.fx.plugin.listeners.PlayerActionHandler;
import io.github.winnpixie.pixiecraft.fx.plugin.utilities.FxConfig;
import io.github.winnpixie.pixiecraft.fx.plugin.utilities.ParticleHelper;
import org.bukkit.plugin.java.JavaPlugin;

public class PxEffectsPlugin extends JavaPlugin {
    private ConfigurationLoader configLoader;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.configLoader = new ConfigurationLoader(getConfig());
        configLoader.link(FxConfig.class);
        configLoader.load();

        ParticleHelper.createBlockData(getServer());

        new EntityActionHandler(this).register();
        new PlayerActionHandler(this).register();
    }
}
