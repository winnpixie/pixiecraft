package io.github.winnpixie.pixiecraft.commons.config;

import org.bukkit.configuration.ConfigurationSection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConfigurationLoader {
    private final List<WrappedField> wrappers = new CopyOnWriteArrayList<>();

    private ConfigurationSection bukkit;

    public ConfigurationLoader(ConfigurationSection bukkit) {
        this.bukkit = bukkit;
    }

    public void linkClass(Class<?> cls) {
        wrapFields(cls.getDeclaredFields(), null);
    }

    public void linkInstance(Object owner) {
        wrapFields(owner.getClass().getDeclaredFields(), owner);
    }

    private void wrapFields(Field[] fields, Object owner) {
        for (Field field : fields) {
            if (!field.canAccess(owner)) {
                continue;
            }

            int modifiers = field.getModifiers();
            if (Modifier.isFinal(modifiers)) {
                continue;
            }

            // FIXME: Ehhhhh
            boolean isStatic = Modifier.isStatic(modifiers);
            if (owner == null && !isStatic) {
                continue;
            }
            if (owner != null && isStatic) {
                continue;
            }

            Linked linker = field.getAnnotation(Linked.class);
            if (linker == null) {
                continue;
            }

            wrappers.add(new WrappedField(field, owner, linker.value()));
        }
    }

    public void load() {
        for (WrappedField wrapper : wrappers) {
            wrapper.setValue(bukkit.get(wrapper.path(), wrapper.getValue()));
        }
    }

    public void reload(ConfigurationSection bukkit) {
        this.bukkit = bukkit;

        load();
    }
}
