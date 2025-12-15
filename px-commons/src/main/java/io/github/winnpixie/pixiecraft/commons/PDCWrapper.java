package io.github.winnpixie.pixiecraft.commons;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class PDCWrapper<P extends JavaPlugin> {
    private static final Map<String, NamespacedKey> KEYS = new HashMap<>();

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

    private NamespacedKey getKeyFromString(String key) {
        return KEYS.computeIfAbsent(key, k -> {
            NamespacedKey nk = NamespacedKey.fromString(k, plugin);
            if (nk == null) {
                throw new IllegalArgumentException("Invalid key!");
            }

            return nk;
        });
    }

    public boolean has(String key) {
        return has(getKeyFromString(key));
    }

    public <S, R> boolean has(String key, PersistentDataType<S, R> type) {
        return has(getKeyFromString(key), type);
    }

    public boolean has(NamespacedKey key) {
        return container.has(key);
    }

    public <S, R> boolean has(NamespacedKey key, PersistentDataType<S, R> type) {
        return container.has(key, type);
    }

    public <S, R> R get(String key, PersistentDataType<S, R> type) {
        return get(getKeyFromString(key), type);
    }

    public <S, R> R get(NamespacedKey key, PersistentDataType<S, R> type) {
        return container.get(key, type);
    }

    public void remove(String key) {
        remove(getKeyFromString(key));
        KEYS.remove(key);
    }

    public void remove(NamespacedKey key) {
        container.remove(key);
    }
}
