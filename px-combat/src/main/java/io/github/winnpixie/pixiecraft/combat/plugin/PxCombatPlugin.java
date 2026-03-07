package io.github.winnpixie.pixiecraft.combat.plugin;

import io.github.winnpixie.pixiecraft.combat.plugin.listeners.PlayerCombatHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class PxCombatPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        new PlayerCombatHandler(this).register();
    }
}
