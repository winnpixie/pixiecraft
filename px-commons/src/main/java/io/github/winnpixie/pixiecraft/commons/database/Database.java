package io.github.winnpixie.pixiecraft.commons.database;

import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;

public class Database<P extends JavaPlugin> {
    private final P plugin;

    private final String host;
    private final String username;
    private final String password;

    private Connection connection;

    public Database(P plugin, String host) {
        this(plugin, host, null, null);
    }

    public Database(P plugin, String host, String username, String password) {
        this.plugin = plugin;

        this.host = host;
        this.username = username;
        this.password = password;
    }

    public P getPlugin() {
        return plugin;
    }

    public boolean connect(String engine) throws SQLException {
        disconnect();

        connection = DriverManager.getConnection("jdbc:%s:%s".formatted(engine, host),
                username, password);

        return !connection.isClosed();
    }

    public void disconnect() throws SQLException {
        if (connection == null) {
            return;
        }

        if (connection.isClosed()) {
            return;
        }

        connection.close();
    }

    public boolean write(String query) throws SQLException {
        write(query, null);

        return true;
    }

    public void write(String query, DatabaseConsumer<PreparedStatement> modifier) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            if (modifier != null) {
                modifier.accept(statement);
            }

            statement.executeUpdate();
        }
    }

    public void read(String query, DatabaseConsumer<ResultSet> onResult) throws SQLException {
        read(query, null, onResult);
    }

    public void read(String query, DatabaseConsumer<PreparedStatement> modifier, DatabaseConsumer<ResultSet> onResult) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            if (modifier != null) {
                modifier.accept(statement);
            }

            try (ResultSet result = statement.executeQuery()) {
                onResult.accept(result);
            }
        }
    }
}
