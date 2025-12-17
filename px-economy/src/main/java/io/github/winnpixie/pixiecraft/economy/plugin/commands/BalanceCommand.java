package io.github.winnpixie.pixiecraft.economy.plugin.commands;

import io.github.winnpixie.pixiecraft.commons.CommonWarnings;
import io.github.winnpixie.pixiecraft.commons.commands.BaseCommand;
import io.github.winnpixie.pixiecraft.economy.api.IUser;
import io.github.winnpixie.pixiecraft.economy.plugin.PxEconomyPlugin;
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
            return false;
        }

        IUser user = getPlugin().getUserManager().get(target);
        if (user == null) {
            sender.spigot().sendMessage(CommonWarnings.INVALID_TARGET);
            return false;
        }

        sender.spigot().sendMessage(new ComponentBuilder(target.getName())
                .color(ChatColor.GREEN)
                .append(" is carrying ")
                .color(ChatColor.DARK_GREEN)
                .append("%.2f".formatted(user.getWallet().getBalance() / 100.00))
                .color(ChatColor.LIGHT_PURPLE)
                .append(" Fairy Dust")
                .color(ChatColor.DARK_PURPLE)
                .build());
        return true;
    }
}
