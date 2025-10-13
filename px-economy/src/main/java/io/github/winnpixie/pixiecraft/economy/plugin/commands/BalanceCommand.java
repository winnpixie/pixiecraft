package io.github.winnpixie.pixiecraft.economy.plugin.commands;

import io.github.winnpixie.pixiecraft.commons.CommonWarnings;
import io.github.winnpixie.pixiecraft.commons.commands.BaseCommand;
import io.github.winnpixie.pixiecraft.economy.plugin.PxEconomyPlugin;
import io.github.winnpixie.pixiecraft.economy.model.IUser;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand extends BaseCommand<PxEconomyPlugin> {
    public BalanceCommand(PxEconomyPlugin plugin) {
        super("balance", plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player target = null;

        if (args.length > 0) {
            target = getPlugin().getServer().getPlayerExact(args[0]);
        } else if (sender instanceof Player player) {
            target = player;
        }

        if (target == null) {
            sender.spigot().sendMessage(CommonWarnings.INVALID_TARGET);
            return true;
        }

        IUser user = getPlugin().getUserManager().get(target);
        if (user == null) {
            sender.spigot().sendMessage(CommonWarnings.INVALID_TARGET);
            return true;
        }

        sender.spigot().sendMessage(new ComponentBuilder("The requested user is carrying ")
                .color(ChatColor.GREEN)
                .append("%.2f".formatted(user.getWallet().getBalance() / 100.00))
                .color(ChatColor.DARK_GREEN)
                .append(" Fairy Dust")
                .color(ChatColor.LIGHT_PURPLE)
                .build());
        return true;
    }
}
