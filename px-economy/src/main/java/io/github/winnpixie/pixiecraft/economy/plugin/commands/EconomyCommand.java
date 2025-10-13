package io.github.winnpixie.pixiecraft.economy.plugin.commands;

import io.github.winnpixie.pixiecraft.commons.commands.BaseCommand;
import io.github.winnpixie.pixiecraft.economy.plugin.PxEconomyPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class EconomyCommand extends BaseCommand<PxEconomyPlugin> {
    public EconomyCommand(PxEconomyPlugin plugin) {
        super("economy", plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
