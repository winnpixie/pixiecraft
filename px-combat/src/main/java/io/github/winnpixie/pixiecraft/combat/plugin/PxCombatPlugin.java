package io.github.winnpixie.pixiecraft.combat.plugin;

import io.github.winnpixie.pixiecraft.combat.plugin.listeners.PlayerCombatHandler;
import io.github.winnpixie.pixiecraft.economy.plugin.PxEconomyPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class PxCombatPlugin extends JavaPlugin {
    private PxEconomyPlugin economy;

    public PxEconomyPlugin getEconomy() {
        return economy;
    }

    @Override
    public void onEnable() {
        this.economy = JavaPlugin.getPlugin(PxEconomyPlugin.class);

        new PlayerCombatHandler(this).register();
    }
}
