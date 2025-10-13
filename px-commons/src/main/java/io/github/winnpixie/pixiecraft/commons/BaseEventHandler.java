package io.github.winnpixie.pixiecraft.commons;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class BaseEventHandler<P extends JavaPlugin> implements Listener {
    private final P plugin;

    public BaseEventHandler(P plugin) {
        this.plugin = plugin;
    }

    public P getPlugin() {
        return plugin;
    }

    public void register() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
