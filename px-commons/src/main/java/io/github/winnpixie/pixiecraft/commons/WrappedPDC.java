package io.github.winnpixie.pixiecraft.commons;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public record WrappedPDC<P extends JavaPlugin>(P plugin,
                                               PersistentDataContainer container) {
    private static final Map<String, NamespacedKey> KEYS = new HashMap<>();

    public WrappedPDC(P plugin, PersistentDataHolder holder) {
        this(plugin, holder.getPersistentDataContainer());
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

    public <S, R> void set(String key, PersistentDataType<S, R> type, R value) {
        set(getKeyFromString(key), type, value);
    }

    public <S, R> void set(NamespacedKey key, PersistentDataType<S, R> type, R value) {
        container.set(key, type, value);
    }

    public void remove(String key) {
        remove(getKeyFromString(key));

        KEYS.remove(key);
    }

    public void remove(NamespacedKey key) {
        container.remove(key);
    }

    // Typed Getters
    public Byte getByte(String key) {
        return getByte(getKeyFromString(key));
    }

    public Byte getByte(NamespacedKey key) {
        return get(key, PersistentDataType.BYTE);
    }

    public Short getShort(String key) {
        return getShort(getKeyFromString(key));
    }

    public Short getShort(NamespacedKey key) {
        return get(key, PersistentDataType.SHORT);
    }

    public Integer getInt(String key) {
        return getInt(getKeyFromString(key));
    }

    public Integer getInt(NamespacedKey key) {
        return get(key, PersistentDataType.INTEGER);
    }

    public Long getLong(String key) {
        return getLong(getKeyFromString(key));
    }

    public Long getLong(NamespacedKey key) {
        return get(key, PersistentDataType.LONG);
    }

    public Float getFloat(String key) {
        return getFloat(getKeyFromString(key));
    }

    public Float getFloat(NamespacedKey key) {
        return get(key, PersistentDataType.FLOAT);
    }

    public Double getDouble(String key) {
        return getDouble(getKeyFromString(key));
    }

    public Double getDouble(NamespacedKey key) {
        return get(key, PersistentDataType.DOUBLE);
    }

    public String getString(String key) {
        return getString(getKeyFromString(key));
    }

    public String getString(NamespacedKey key) {
        return get(key, PersistentDataType.STRING);
    }

    public Boolean getBoolean(String key) {
        return getBoolean(getKeyFromString(key));
    }

    public Boolean getBoolean(NamespacedKey key) {
        return get(key, PersistentDataType.BOOLEAN);
    }

    // Typed Setters
    public void setByte(String key, Byte value) {
        setByte(getKeyFromString(key), value);
    }

    public void setByte(NamespacedKey key, Byte value) {
        set(key, PersistentDataType.BYTE, value);
    }

    public void setShort(String key, Short value) {
        setShort(getKeyFromString(key), value);
    }

    public void setShort(NamespacedKey key, Short value) {
        set(key, PersistentDataType.SHORT, value);
    }

    public void setInt(String key, Integer value) {
        setInt(getKeyFromString(key), value);
    }

    public void setInt(NamespacedKey key, Integer value) {
        set(key, PersistentDataType.INTEGER, value);
    }

    public void setLong(String key, Long value) {
        setLong(getKeyFromString(key), value);
    }

    public void setLong(NamespacedKey key, Long value) {
        set(key, PersistentDataType.LONG, value);
    }

    public void setFloat(String key, Float value) {
        setFloat(getKeyFromString(key), value);
    }

    public void setFloat(NamespacedKey key, Float value) {
        set(key, PersistentDataType.FLOAT, value);
    }

    public void setDouble(String key, Double value) {
        setDouble(getKeyFromString(key), value);
    }

    public void setDouble(NamespacedKey key, Double value) {
        set(key, PersistentDataType.DOUBLE, value);
    }

    public void setString(String key, String value) {
        setString(getKeyFromString(key), value);
    }

    public void setString(NamespacedKey key, String value) {
        set(key, PersistentDataType.STRING, value);
    }

    public void setBoolean(String key, Boolean value) {
        setBoolean(getKeyFromString(key), value);
    }

    public void setBoolean(NamespacedKey key, Boolean value) {
        set(key, PersistentDataType.BOOLEAN, value);
    }
}
