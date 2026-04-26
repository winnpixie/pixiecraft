package io.github.winnpixie.pixiecraft.agriculture.plugin;

import io.github.winnpixie.pixiecraft.agriculture.plugin.commands.ToggleStrippingCommand;
import io.github.winnpixie.pixiecraft.agriculture.plugin.handlers.PlayerActionHandler;
import io.github.winnpixie.pixiecraft.agriculture.plugin.handlers.PlayerConnectionHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class PxAgriculturePlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        new PlayerConnectionHandler(this).register();
        new PlayerActionHandler(this).register();

        new ToggleStrippingCommand(this).register();
    }
}
