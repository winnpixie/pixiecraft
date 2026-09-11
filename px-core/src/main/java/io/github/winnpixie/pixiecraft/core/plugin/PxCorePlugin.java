package io.github.winnpixie.pixiecraft.core.plugin;

import io.github.winnpixie.pixiecraft.commons.config.ConfigurationLoader;
import io.github.winnpixie.pixiecraft.core.plugin.commands.HatCommand;
import io.github.winnpixie.pixiecraft.core.plugin.commands.PingCommand;
import io.github.winnpixie.pixiecraft.core.plugin.commands.SeenCommand;
import io.github.winnpixie.pixiecraft.core.plugin.commands.StoreExperienceCommand;
import io.github.winnpixie.pixiecraft.core.plugin.handlers.ConnectionHandler;
import io.github.winnpixie.pixiecraft.core.plugin.handlers.EntityActionHandler;
import io.github.winnpixie.pixiecraft.core.plugin.handlers.PlayerActionHandler;
import io.github.winnpixie.pixiecraft.economy.plugin.PxEconomyPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class PxCorePlugin extends JavaPlugin {
    private PxEconomyPlugin economy;
    private ConfigurationLoader configLoader;

    public PxEconomyPlugin getEconomy() {
        return economy;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.configLoader = new ConfigurationLoader(getConfig());
        configLoader.link(CoreConfig.class);
        configLoader.load();

        this.economy = JavaPlugin.getPlugin(PxEconomyPlugin.class);

        new ConnectionHandler(this).register();
        new PlayerActionHandler(this).register();
        new EntityActionHandler(this).register();

        new HatCommand(this).register();
        new PingCommand(this).register();
        new SeenCommand(this).register();
        new StoreExperienceCommand(this).register();
    }
}
