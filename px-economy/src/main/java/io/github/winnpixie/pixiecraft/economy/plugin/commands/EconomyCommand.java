package io.github.winnpixie.pixiecraft.economy.plugin.commands;

import io.github.winnpixie.pixiecraft.commons.CommonWarnings;
import io.github.winnpixie.pixiecraft.commons.MathHelper;
import io.github.winnpixie.pixiecraft.commons.commands.BaseCommand;
import io.github.winnpixie.pixiecraft.economy.api.IUser;
import io.github.winnpixie.pixiecraft.economy.api.IWallet;
import io.github.winnpixie.pixiecraft.economy.plugin.EconomyWarnings;
import io.github.winnpixie.pixiecraft.economy.plugin.PxEconomyPlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EconomyCommand extends BaseCommand<PxEconomyPlugin> {
    public EconomyCommand(PxEconomyPlugin plugin) {
        super("economy", plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("eco.manage")) {
            sender.spigot().sendMessage(CommonWarnings.LACKS_PERMISSION);
            return false;
        }

        if (args.length < 2) {
            return false;
        }

        Player target = getPlugin().getServer().getPlayerExact(args[1]);
        if (target == null) {
            sender.spigot().sendMessage(CommonWarnings.INVALID_TARGET);
            return false;
        }

        IUser user = getPlugin().getUserManager().get(target);
        IWallet wallet = user.getWallet();

        return switch (args[0].toLowerCase()) {
            case "balance" -> showBalance(sender, target, wallet);
            case "grant" -> {
                if (args.length < 3) {
                    yield false;
                }

                yield grant(sender, target, wallet, args[2]);
            }
            case "tax" -> {
                if (args.length < 3) {
                    yield false;
                }

                yield tax(sender, target, wallet, args[2]);
            }
            default -> false;
        };
    }

    private boolean showBalance(CommandSender sender, Player target, IWallet wallet) {
        if (wallet == null) {
            sender.spigot().sendMessage(EconomyWarnings.INVALID_ACCOUNT);
            return false;
        }

        sender.spigot().sendMessage(new ComponentBuilder(target.getName())
                .color(ChatColor.GREEN)
                .append(" currently has ")
                .color(ChatColor.DARK_GREEN)
                .append(String.format("%.2f", wallet.getBalance() / 100.00))
                .color(ChatColor.LIGHT_PURPLE)
                .append(" Fairy Dust")
                .color(ChatColor.DARK_PURPLE)
                .build());
        return true;
    }

    private boolean grant(CommandSender sender, Player target, IWallet wallet, String requestedAmount) {
        if (!MathHelper.isDouble(requestedAmount)) {
            sender.spigot().sendMessage(CommonWarnings.INVALID_ARG_TYPE);
            return false;
        }

        double parsed = Double.parseDouble(requestedAmount);
        long amount = (long) (parsed * 100.00);

        wallet.earn(amount);

        sender.spigot().sendMessage(new ComponentBuilder("Granted ")
                .color(ChatColor.DARK_GREEN)
                .append(String.format("%.2f", parsed))
                .color(ChatColor.LIGHT_PURPLE)
                .append(" Fairy Dust")
                .color(ChatColor.DARK_PURPLE)
                .append(" to ")
                .color(ChatColor.DARK_GREEN)
                .append(target.getName())
                .color(ChatColor.GREEN)
                .build());
        return true;
    }

    private boolean tax(CommandSender sender, Player target, IWallet wallet, String requestedAmount) {
        if (!MathHelper.isDouble(requestedAmount)) {
            sender.spigot().sendMessage(CommonWarnings.INVALID_ARG_TYPE);
            return false;
        }

        double parsed = Double.parseDouble(requestedAmount);
        long amount = (long) (parsed * 100.00);
        if (!wallet.spend(amount)) {
            sender.spigot().sendMessage(EconomyWarnings.INSUFFICIENT_FUNDS);
            return false;
        }

        sender.spigot().sendMessage(new ComponentBuilder("Taxed ")
                .color(ChatColor.DARK_GREEN)
                .append(String.format("%.2f", parsed))
                .color(ChatColor.LIGHT_PURPLE)
                .append(" Fairy Dust")
                .color(ChatColor.DARK_PURPLE)
                .append(" from ")
                .color(ChatColor.DARK_GREEN)
                .append(target.getName())
                .color(ChatColor.GREEN)
                .build());
        return true;
    }
}
