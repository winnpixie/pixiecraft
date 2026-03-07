package io.github.winnpixie.pixiecraft.commons.database;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;

public class SQLite<P extends JavaPlugin> extends Database<P> {
    public SQLite(P plugin, String name) {
        super(plugin, new File(plugin.getDataFolder(), "%s.db".formatted(name)).getAbsolutePath());
    }

    public SQLite(P plugin, String name, String username, String password) {
        super(plugin, new File(plugin.getDataFolder(), "%s.db".formatted(name)).getAbsolutePath(), username, password);
    }

    public void connect() throws SQLException {
        connect("sqlite");
    }
}
