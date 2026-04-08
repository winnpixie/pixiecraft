package io.github.winnpixie.pixiecraft.economy.plugin;

import io.github.winnpixie.pixiecraft.commons.database.SQLite;
import io.github.winnpixie.pixiecraft.economy.plugin.commands.BalanceCommand;
import io.github.winnpixie.pixiecraft.economy.plugin.commands.BankCommand;
import io.github.winnpixie.pixiecraft.economy.plugin.commands.EconomyCommand;
import io.github.winnpixie.pixiecraft.economy.plugin.handlers.PlayerConnectionHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.logging.Level;

public class PxEconomyPlugin extends JavaPlugin {
    private static PxEconomyPlugin instance;

    private final SQLite<PxEconomyPlugin> database = new SQLite<>(this, "economy");
    private final UserManager userManager = new UserManager(database);
    private final CentralBank centralBank = new CentralBank(database);

    public static PxEconomyPlugin getInstance() {
        return instance;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public CentralBank getCentralBank() {
        return centralBank;
    }

    @Override
    public void onLoad() {
        if (instance == null) {
            instance = this;
        }
    }

    @Override
    public void onEnable() {
        loadDatabase();

        registerHandlers();
        registerCommands();
    }

    private void loadDatabase() {
        try {
            database.connect();
        } catch (SQLException e) {
            getLogger().log(Level.WARNING, "Error connecting to database", e);
        }
    }

    private void registerHandlers() {
        new PlayerConnectionHandler(this).register();
    }

    private void registerCommands() {
        new EconomyCommand(this).register();
        new BalanceCommand(this).register();
        new BankCommand(this).register();
    }

    @Override
    public void onDisable() {
        saveDatabase();
    }

    private void saveDatabase() {
        userManager.flush();
        centralBank.flush();

        try {
            database.disconnect();
        } catch (SQLException e) {
            getLogger().log(Level.WARNING, "Error flushing database", e);
        }
    }
}
