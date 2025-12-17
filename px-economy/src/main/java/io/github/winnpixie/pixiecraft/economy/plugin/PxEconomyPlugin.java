package io.github.winnpixie.pixiecraft.economy.plugin;

import io.github.winnpixie.pixiecraft.economy.api.IBank;
import io.github.winnpixie.pixiecraft.economy.impl.Bank;
import io.github.winnpixie.pixiecraft.economy.plugin.commands.BalanceCommand;
import io.github.winnpixie.pixiecraft.economy.plugin.commands.BankCommand;
import io.github.winnpixie.pixiecraft.economy.plugin.commands.EconomyCommand;
import io.github.winnpixie.pixiecraft.economy.plugin.handlers.PlayerConnectionHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class PxEconomyPlugin extends JavaPlugin {
    private final UserManager userManager = new UserManager();
    private final IBank centralBank = new Bank("Central");

    public UserManager getUserManager() {
        return userManager;
    }

    public IBank getCentralBank() {
        return centralBank;
    }

    @Override
    public void onEnable() {
        new PlayerConnectionHandler(this).register();

        new EconomyCommand(this).register();
        new BalanceCommand(this).register();
        new BankCommand(this).register();
    }

    @Override
    public void onDisable() {
    }
}
