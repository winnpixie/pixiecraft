package io.github.winnpixie.pixiecraft.economy.plugin.commands;

import io.github.winnpixie.pixiecraft.commons.WarningMessages;
import io.github.winnpixie.pixiecraft.commons.commands.BaseCommand;
import io.github.winnpixie.pixiecraft.economy.api.IUser;
import io.github.winnpixie.pixiecraft.economy.api.IWallet;
import io.github.winnpixie.pixiecraft.economy.plugin.EconomyConfig;
import io.github.winnpixie.pixiecraft.economy.plugin.EconomyWarnings;
import io.github.winnpixie.pixiecraft.economy.plugin.PxEconomyPlugin;
import io.github.winnpixie.pixiecraft.economy.plugin.UnitConverter;
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
            sender.spigot().sendMessage(WarningMessages.LACKS_PERMISSION);
            return false;
        }

        if (args.length < 2) {
            sender.spigot().sendMessage(WarningMessages.MISSING_PARAMETERS);
            return false;
        }

        Player player = getPlugin().getServer().getPlayerExact(args[1]);
        if (player == null) {
            sender.spigot().sendMessage(WarningMessages.INVALID_TARGET);
            return false;
        }

        IUser user = getPlugin().getUserManager().get(player);
        IWallet wallet = user.getWallet();

        return switch (args[0].toLowerCase()) {
            case "balance" -> showBalance(sender, player, wallet);
            case "grant" -> {
                if (args.length < 3) {
                    yield false;
                }

                yield grant(sender, player, wallet, args[2]);
            }
            case "tax" -> {
                if (args.length < 3) {
                    yield false;
                }

                yield tax(sender, player, wallet, args[2]);
            }
            default -> false;
        };
    }

    private boolean showBalance(CommandSender sender, Player player, IWallet wallet) {
        if (wallet == null) {
            sender.spigot().sendMessage(EconomyWarnings.INVALID_ACCOUNT);
            return false;
        }

        sender.spigot().sendMessage(new ComponentBuilder(player.getName())
                .color(ChatColor.GREEN)
                .append(" currently has ")
                .color(ChatColor.DARK_GREEN)
                .append(UnitConverter.toString(wallet.getBalance()))
                .color(ChatColor.LIGHT_PURPLE)
                .append(" %s".formatted(EconomyConfig.CURRENCY_NAME))
                .color(ChatColor.DARK_PURPLE)
                .build());
        return true;
    }

    private boolean grant(CommandSender sender, Player player, IWallet wallet, String requestedAmount) {
        if (!UnitConverter.isUnit(requestedAmount)) {
            sender.spigot().sendMessage(WarningMessages.WRONG_ARGUMENT_TYPE);
            return false;
        }

        long amount = UnitConverter.valueOf(requestedAmount);
        if (!wallet.earn(amount)) {
            sender.spigot().sendMessage(EconomyWarnings.INSUFFICIENT_FUNDS);
            return false;
        }

        sender.spigot().sendMessage(new ComponentBuilder("Granted ")
                .color(ChatColor.DARK_GREEN)
                .append(UnitConverter.toString(amount))
                .color(ChatColor.LIGHT_PURPLE)
                .append(" %s".formatted(EconomyConfig.CURRENCY_NAME))
                .color(ChatColor.DARK_PURPLE)
                .append(" to ")
                .color(ChatColor.DARK_GREEN)
                .append(player.getName())
                .color(ChatColor.GREEN)
                .build());
        return true;
    }

    private boolean tax(CommandSender sender, Player player, IWallet wallet, String requestedAmount) {
        if (!UnitConverter.isUnit(requestedAmount)) {
            sender.spigot().sendMessage(WarningMessages.WRONG_ARGUMENT_TYPE);
            return false;
        }

        long amount = UnitConverter.valueOf(requestedAmount);
        if (!wallet.spend(amount)) {
            sender.spigot().sendMessage(EconomyWarnings.INSUFFICIENT_FUNDS);
            return false;
        }

        sender.spigot().sendMessage(new ComponentBuilder("Taxed ")
                .color(ChatColor.DARK_GREEN)
                .append(UnitConverter.toString(amount))
                .color(ChatColor.LIGHT_PURPLE)
                .append(" %s".formatted(EconomyConfig.CURRENCY_NAME))
                .color(ChatColor.DARK_PURPLE)
                .append(" from ")
                .color(ChatColor.DARK_GREEN)
                .append(player.getName())
                .color(ChatColor.GREEN)
                .build());
        return true;
    }
}
