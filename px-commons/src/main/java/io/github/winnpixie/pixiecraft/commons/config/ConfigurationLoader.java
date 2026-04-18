package io.github.winnpixie.pixiecraft.commons.config;

import org.bukkit.configuration.ConfigurationSection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConfigurationLoader {
    private final List<FieldWrapper> wrappers = new CopyOnWriteArrayList<>();

    private ConfigurationSection bukkit;

    public ConfigurationLoader(ConfigurationSection bukkit) {
        this.bukkit = bukkit;
    }

    public void link(Object owner) {
        Object obj = owner;
        Field[] fields;

        if (owner instanceof Class<?> cls) {
            obj = null;
            fields = cls.getDeclaredFields();
        } else {
            fields = owner.getClass().getDeclaredFields();
        }

        for (Field field : fields) {
            if (Modifier.isFinal(field.getModifiers())
                    || !field.canAccess(obj)) {
                continue;
            }

            Linked linker = field.getAnnotation(Linked.class);
            if (linker == null) {
                continue;
            }

            try {
                wrappers.add(new FieldWrapper(field, obj, linker.value()));
            } catch (IllegalAccessException iae) {
                iae.printStackTrace();
            }
        }
    }


    public void load() {
        for (FieldWrapper handler : wrappers) {
            handler.set(bukkit.get(handler.path(), handler.get()));
        }
    }

    public void reload(ConfigurationSection bukkit) {
        this.bukkit = bukkit;

        load();
    }
}
