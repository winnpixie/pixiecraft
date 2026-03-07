package io.github.winnpixie.pixiecraft.commons.config;

import java.lang.reflect.Field;

public record WrappedField(Field field,
                           Object owner,
                           String path) {
    public void setValue(Object value) {
        try {
            field.set(owner, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Object getValue() {
        try {
            return field.get(owner);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
