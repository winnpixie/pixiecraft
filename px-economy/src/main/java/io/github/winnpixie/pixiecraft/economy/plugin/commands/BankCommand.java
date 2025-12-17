package io.github.winnpixie.pixiecraft.economy.plugin.commands;

import io.github.winnpixie.pixiecraft.commons.CommonWarnings;
import io.github.winnpixie.pixiecraft.commons.MathHelper;
import io.github.winnpixie.pixiecraft.commons.commands.PlayerCommand;
import io.github.winnpixie.pixiecraft.economy.api.IAccount;
import io.github.winnpixie.pixiecraft.economy.api.IAccountHolder;
import io.github.winnpixie.pixiecraft.economy.api.IUser;
import io.github.winnpixie.pixiecraft.economy.plugin.EconomyWarnings;
import io.github.winnpixie.pixiecraft.economy.plugin.PxEconomyPlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class BankCommand extends PlayerCommand<PxEconomyPlugin> {
    public BankCommand(PxEconomyPlugin plugin) {
        super("bank", plugin);
    }

    @Override
    public boolean execute(Player player, Command command, String label, String[] args) {
        if (args.length < 1) {
            player.spigot().sendMessage(CommonWarnings.NOT_ENOUGH_ARGS);
            return false;
        }

        IUser user = getPlugin().getUserManager().get(player);
        IAccountHolder holder = getPlugin().getCentralBank().find(user);

        if (args[0].equalsIgnoreCase("accounts")) {
            for (IAccount account : holder.getAccounts()) {
                player.spigot().sendMessage(new ComponentBuilder(account.getName())
                        .color(ChatColor.GREEN)
                        .append(": ")
                        .color(ChatColor.DARK_GREEN)
                        .append(String.format("%.2f", account.getBalance() / 100.00))
                        .color(ChatColor.LIGHT_PURPLE)
                        .append(" Fairy Dust")
                        .color(ChatColor.DARK_PURPLE)
                        .build());
            }

            return true;
        } else if (args.length < 2) {
            player.spigot().sendMessage(CommonWarnings.NOT_ENOUGH_ARGS);
            return false;
        }

        IAccount account = holder.find(args[1]);

        return switch (args[0].toLowerCase()) {
            case "open" -> openAccount(player, holder, account, args[1]);
            case "close" -> closeAccount(player, holder, account);
            case "balance" -> showBalance(player, account);
            case "deposit" -> {
                if (args.length < 3) {
                    yield false;
                }

                yield deposit(player, user, account, args[2]);
            }
            case "withdraw" -> {
                if (args.length < 3) {
                    yield false;
                }

                yield withdraw(player, user, account, args[2]);
            }
            default -> false;
        };
    }

    private boolean openAccount(Player player, IAccountHolder holder, IAccount existing, String name) {
        if (existing != null) {
            player.spigot().sendMessage(new ComponentBuilder("Account ")
                    .color(ChatColor.YELLOW)
                    .append(existing.getName())
                    .color(ChatColor.GREEN)
                    .append(" already exists!")
                    .color(ChatColor.YELLOW)
                    .build());
            return false;
        }

        holder.open(name);

        player.spigot().sendMessage(new ComponentBuilder("Opened account: ")
                .color(ChatColor.DARK_GREEN)
                .append(name)
                .color(ChatColor.GREEN)
                .build());
        return true;
    }

    private boolean closeAccount(Player player, IAccountHolder holder, IAccount account) {
        if (account == null) {
            player.spigot().sendMessage(EconomyWarnings.INVALID_ACCOUNT);
            return false;
        }

        holder.close(account);
        return true;
    }

    private boolean showBalance(Player player, IAccount account) {
        if (account == null) {
            player.spigot().sendMessage(EconomyWarnings.INVALID_ACCOUNT);
            return false;
        }

        player.spigot().sendMessage(new ComponentBuilder(account.getName())
                .color(ChatColor.GREEN)
                .append(" currently has ")
                .color(ChatColor.DARK_GREEN)
                .append(String.format("%.2f", account.getBalance() / 100.00))
                .color(ChatColor.LIGHT_PURPLE)
                .append(" Fairy Dust")
                .color(ChatColor.DARK_PURPLE)
                .build());
        return true;
    }

    private boolean deposit(Player player, IUser user, IAccount account, String requestedAmount) {
        if (account == null) {
            player.spigot().sendMessage(EconomyWarnings.INVALID_ACCOUNT);
            return false;
        }

        if (!MathHelper.isDouble(requestedAmount)) {
            player.spigot().sendMessage(CommonWarnings.INVALID_ARG_TYPE);
            return false;
        }

        double parsed = Double.parseDouble(requestedAmount);
        long amount = (long) (parsed * 100.00);
        if (!user.getWallet().withdraw(amount)) {
            player.spigot().sendMessage(EconomyWarnings.INSUFFICIENT_WALLET_FUNDS);
            return false;
        }

        account.deposit(amount);

        player.spigot().sendMessage(new ComponentBuilder("Deposited ")
                .color(ChatColor.DARK_GREEN)
                .append(String.format("%.2f", parsed))
                .color(ChatColor.LIGHT_PURPLE)
                .append(" Fairy Dust")
                .color(ChatColor.DARK_PURPLE)
                .append(" into ")
                .color(ChatColor.DARK_GREEN)
                .append(account.getName())
                .color(ChatColor.GREEN)
                .build());
        return true;
    }

    private boolean withdraw(Player player, IUser user, IAccount account, String requestedAmount) {
        if (account == null) {
            player.spigot().sendMessage(EconomyWarnings.INVALID_ACCOUNT);
            return false;
        }

        if (!MathHelper.isDouble(requestedAmount)) {
            player.spigot().sendMessage(CommonWarnings.INVALID_ARG_TYPE);
            return false;
        }

        double parsed = Double.parseDouble(requestedAmount);
        long amount = (long) (parsed * 100.00);
        if (!account.withdraw(amount)) {
            player.spigot().sendMessage(EconomyWarnings.INSUFFICIENT_FUNDS);
            return false;
        }

        user.getWallet().deposit(amount);

        player.spigot().sendMessage(new ComponentBuilder("Withdrew ")
                .color(ChatColor.DARK_GREEN)
                .append(String.format("%.2f", parsed))
                .color(ChatColor.LIGHT_PURPLE)
                .append(" Fairy Dust")
                .color(ChatColor.DARK_PURPLE)
                .append(" from ")
                .color(ChatColor.DARK_GREEN)
                .append(account.getName())
                .color(ChatColor.GREEN)
                .build());
        return true;
    }
}
