package io.github.winnpixie.pixiecraft.mature.plugin;

import io.github.winnpixie.pixiecraft.mature.plugin.commands.CoinFlipCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class PxMaturePlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        new CoinFlipCommand(this).register();
    }
}
