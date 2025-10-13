package io.github.winnpixie.pixiecraft.commons;

import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.java.JavaPlugin;

public class PDCWrapper<P extends JavaPlugin> {
    private final P plugin;
    private final PersistentDataContainer container;

    public PDCWrapper(P plugin, PersistentDataContainer container) {
        this.plugin = plugin;
        this.container = container;
    }

    public P getPlugin() {
        return plugin;
    }

    public PersistentDataContainer getContainer() {
        return container;
    }
}
