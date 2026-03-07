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

    private final UserManager userManager = new UserManager();
    private final CentralBank centralBank = new CentralBank();

    private final SQLite<PxEconomyPlugin> database = new SQLite<>(this, "economy");

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
        try {
            database.connect();

            userManager.loadUsers(database);
            centralBank.load(database);
        } catch (SQLException e) {
            getLogger().log(Level.WARNING, "Error loading from database", e);
        }

        new PlayerConnectionHandler(this).register();

        new EconomyCommand(this).register();
        new BalanceCommand(this).register();
        new BankCommand(this).register();
    }

    @Override
    public void onDisable() {
        // don't judge me, watchers
        // THIS CODE IS TEST ILL WRITE A PROPER SAVE SYSTEM
        userManager.saveUsers(database);
        centralBank.save(database);

        try {
            database.disconnect();
        } catch (SQLException e) {
            getLogger().log(Level.WARNING, "Error closing database", e);
        }
    }
}
