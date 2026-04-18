package io.github.winnpixie.pixiecraft.commons.config;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;

public record FieldWrapper(VarHandle handle,
                           Object owner,
                           String path) {
    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    FieldWrapper(Field field, Object owner, String path) throws IllegalAccessException {
        this(LOOKUP.unreflectVarHandle(field), owner, path);
    }

    public void set(Object value) {
        if (owner == null) {
            handle.set(value);
        } else {
            handle.set(owner, value);
        }
    }

    public Object get() {
        return owner == null ?
                handle.get() : handle.get(owner);
    }
}
