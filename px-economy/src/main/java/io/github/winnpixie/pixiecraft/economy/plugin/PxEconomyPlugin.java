package io.github.winnpixie.pixiecraft.economy.plugin;

import io.github.winnpixie.pixiecraft.commons.config.ConfigurationLoader;
import io.github.winnpixie.pixiecraft.commons.database.SQLite;
import io.github.winnpixie.pixiecraft.economy.api.IBank;
import io.github.winnpixie.pixiecraft.economy.plugin.commands.BalanceCommand;
import io.github.winnpixie.pixiecraft.economy.plugin.commands.BankCommand;
import io.github.winnpixie.pixiecraft.economy.plugin.commands.EconomyCommand;
import io.github.winnpixie.pixiecraft.economy.plugin.commands.PayCommand;
import io.github.winnpixie.pixiecraft.economy.plugin.handlers.PlayerConnectionHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.logging.Level;

public class PxEconomyPlugin extends JavaPlugin {
    private final SQLite<PxEconomyPlugin> database = new SQLite<>(this, "economy");
    private final UserManager userManager = new UserManager(database);
    private final BankRegistry bankRegistry = new BankRegistry(database, userManager);

    private ConfigurationLoader configLoader;
    private IBank centralBank;

    public UserManager getUserManager() {
        return userManager;
    }

    public BankRegistry getBankRegistry() {
        return bankRegistry;
    }

    public IBank getCentralBank() {
        return centralBank;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.configLoader = new ConfigurationLoader(getConfig());
        configLoader.link(EconomyConfig.class);
        configLoader.load();

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

        this.centralBank = bankRegistry.load("central");
    }

    private void registerHandlers() {
        new PlayerConnectionHandler(this).register();
    }

    private void registerCommands() {
        new EconomyCommand(this).register();
        new BalanceCommand(this).register();
        new BankCommand(this).register();
        new PayCommand(this).register();
    }

    @Override
    public void onDisable() {
        saveDatabase();
    }

    private void saveDatabase() {
        userManager.flush();
        bankRegistry.flush();

        try {
            database.disconnect();
        } catch (SQLException e) {
            getLogger().log(Level.WARNING, "Error flushing database", e);
        }
    }
}
