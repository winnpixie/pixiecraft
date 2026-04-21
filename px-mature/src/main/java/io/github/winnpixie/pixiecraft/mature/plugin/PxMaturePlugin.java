package io.github.winnpixie.pixiecraft.mature.plugin;

import io.github.winnpixie.pixiecraft.economy.plugin.PxEconomyPlugin;
import io.github.winnpixie.pixiecraft.mature.plugin.commands.CoinFlipCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class PxMaturePlugin extends JavaPlugin {
    private PxEconomyPlugin economy;

    public PxEconomyPlugin getEconomy() {
        return economy;
    }

    @Override
    public void onEnable() {
        this.economy = JavaPlugin.getPlugin(PxEconomyPlugin.class);

        new CoinFlipCommand(this).register();
    }
}
