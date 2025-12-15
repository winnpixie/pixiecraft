package io.github.winnpixie.pixiecraft.economy.plugin;

import io.github.winnpixie.pixiecraft.economy.impl.Bank;
import io.github.winnpixie.pixiecraft.economy.plugin.commands.BalanceCommand;
import io.github.winnpixie.pixiecraft.economy.plugin.commands.EconomyCommand;
import io.github.winnpixie.pixiecraft.economy.plugin.handlers.PlayerConnectionHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class PxEconomyPlugin extends JavaPlugin {
    private final UserManager userManager = new UserManager();
    private final Bank centralBank = new Bank("Central");

    public UserManager getUserManager() {
        return userManager;
    }

    public Bank getCentralBank() {
        return centralBank;
    }

    @Override
    public void onEnable() {
        new PlayerConnectionHandler(this).register();

        new EconomyCommand(this).register();
        new BalanceCommand(this).register();
    }

    @Override
    public void onDisable() {
    }
}
