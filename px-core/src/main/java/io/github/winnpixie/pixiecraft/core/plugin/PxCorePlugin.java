package io.github.winnpixie.pixiecraft.core.plugin;

import io.github.winnpixie.pixiecraft.core.plugin.commands.HatCommand;
import io.github.winnpixie.pixiecraft.core.plugin.commands.PingCommand;
import io.github.winnpixie.pixiecraft.core.plugin.commands.SeenCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class PxCorePlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        new HatCommand(this).register();
        new PingCommand(this).register();
        new SeenCommand(this).register();
    }
}
