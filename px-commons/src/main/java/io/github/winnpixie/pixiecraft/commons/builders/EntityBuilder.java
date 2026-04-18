package io.github.winnpixie.pixiecraft.commons.builders;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.function.Consumer;


public class EntityBuilder<E extends Entity> {
    private final Class<E> type;

    private Location location;
    private String displayName;
    private boolean glowing;
    private boolean invulnerable;
    private boolean silent;
    private boolean gravity = true;

    private EntityBuilder(Class<E> type) {
        this.type = type;
    }

    public static <T extends Entity> EntityBuilder<T> of(Class<T> type) {
        return new EntityBuilder<>(type);
    }

    public EntityBuilder<E> at(Location location) {
        this.location = location;
        return this;
    }

    public EntityBuilder<E> name(String name) {
        this.displayName = name;
        return this;
    }

    public EntityBuilder<E> glowing(boolean glow) {
        this.glowing = glow;
        return this;
    }

    public EntityBuilder<E> invulnerable(boolean invulnerable) {
        this.invulnerable = invulnerable;
        return this;
    }

    public EntityBuilder<E> silent(boolean silent) {
        this.silent = silent;
        return this;
    }

    public EntityBuilder<E> gravity(boolean gravity) {
        this.gravity = gravity;
        return this;
    }

    public E spawn() {
        return location.getWorld().spawn(location, type, entity -> {
            entity.setCustomName(displayName);
            entity.setGlowing(glowing);
            entity.setInvulnerable(invulnerable);
            entity.setSilent(silent);
            entity.setGravity(gravity);
        });
    }

    public E spawn(Consumer<E> mutator) {
        return location.getWorld().spawn(location, type, entity -> {
            entity.setCustomName(displayName);
            entity.setGlowing(glowing);
            entity.setInvulnerable(invulnerable);
            entity.setSilent(silent);
            entity.setGravity(gravity);

            mutator.accept(entity);
        });
    }
}