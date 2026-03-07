package io.github.winnpixie.pixiecraft.commons.database;

import java.sql.SQLException;

public interface DatabaseConsumer<T> {
    void accept(T value) throws SQLException;
}
