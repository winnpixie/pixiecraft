package io.github.winnpixie.pixiecraft.economy.plugin.commands;

import io.github.winnpixie.pixiecraft.commons.CommonWarnings;
import io.github.winnpixie.pixiecraft.commons.commands.PlayerCommand;
import io.github.winnpixie.pixiecraft.economy.api.IUser;
import io.github.winnpixie.pixiecraft.economy.api.IWallet;
import io.github.winnpixie.pixiecraft.economy.plugin.EconomyConfig;
import io.github.winnpixie.pixiecraft.economy.plugin.EconomyWarnings;
import io.github.winnpixie.pixiecraft.economy.plugin.PxEconomyPlugin;
import io.github.winnpixie.pixiecraft.economy.plugin.UnitConverter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class PayCommand extends PlayerCommand<PxEconomyPlugin> {
    public PayCommand(PxEconomyPlugin plugin) {
        super("pay", plugin);
    }

    @Override
    public boolean execute(Player player, Command command, String label, String[] args) {
        if (args.length < 2) {
            player.spigot().sendMessage(CommonWarnings.MISSING_PARAMETERS);
            return false;
        }

        Player receiver = getPlugin().getServer().getPlayerExact(args[0]);
        if (receiver == null) {
            player.spigot().sendMessage(CommonWarnings.INVALID_TARGET);
            return false;
        }

        String requestedAmount = args[1];
        if (!UnitConverter.isUnit(requestedAmount)) {
            player.spigot().sendMessage(CommonWarnings.WRONG_ARGUMENT_TYPE);
            return false;
        }

        IUser payer = getPlugin().getUserManager().get(player);
        IWallet payerWallet = payer.getWallet();

        long amount = UnitConverter.fromString(requestedAmount);
        if (!payerWallet.spend(amount)) {
            player.spigot().sendMessage(EconomyWarnings.INSUFFICIENT_WALLET_FUNDS);
            return false;
        }

        IUser payee = getPlugin().getUserManager().get(receiver);
        IWallet payeeWallet = payee.getWallet();
        payeeWallet.earn(amount);

        // Tell the payee they've received currency
        receiver.spigot().sendMessage(new ComponentBuilder("Received ")
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

        // Tell the payer they've transferred their balance
        player.spigot().sendMessage(new ComponentBuilder("Sent ")
                .color(ChatColor.DARK_GREEN)
                .append(UnitConverter.toString(amount))
                .color(ChatColor.LIGHT_PURPLE)
                .append(" %s".formatted(EconomyConfig.CURRENCY_NAME))
                .color(ChatColor.DARK_PURPLE)
                .append(" to ")
                .color(ChatColor.DARK_GREEN)
                .append(receiver.getName())
                .color(ChatColor.GREEN)
                .build());
        return true;
    }
}
