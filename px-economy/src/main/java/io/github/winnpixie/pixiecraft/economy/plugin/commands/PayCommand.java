package io.github.winnpixie.pixiecraft.economy.plugin.commands;

import io.github.winnpixie.pixiecraft.commons.CommonWarnings;
import io.github.winnpixie.pixiecraft.commons.MathHelper;
import io.github.winnpixie.pixiecraft.commons.commands.PlayerCommand;
import io.github.winnpixie.pixiecraft.economy.api.IUser;
import io.github.winnpixie.pixiecraft.economy.api.IWallet;
import io.github.winnpixie.pixiecraft.economy.plugin.EconomyWarnings;
import io.github.winnpixie.pixiecraft.economy.plugin.PxEconomyPlugin;
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
            return false;
        }

        Player target = getPlugin().getServer().getPlayerExact(args[0]);
        if (target == null) {
            player.spigot().sendMessage(CommonWarnings.INVALID_TARGET);
            return false;
        }

        String requestedAmount = args[1];
        if (!MathHelper.isDouble(requestedAmount)) {
            player.spigot().sendMessage(CommonWarnings.INVALID_ARG_TYPE);
            return false;
        }

        IUser payer = getPlugin().getUserManager().get(player);
        IWallet payerWallet = payer.getWallet();

        double parsed = Double.parseDouble(requestedAmount);
        long amount = (long) (parsed * 100.00);

        if (!payerWallet.spend(amount)) {
            player.spigot().sendMessage(EconomyWarnings.INSUFFICIENT_WALLET_FUNDS);
            return false;
        }

        IUser payee = getPlugin().getUserManager().get(target);
        IWallet payeeWallet = payee.getWallet();
        payeeWallet.earn(amount);

        // Tell the payee they've received currency
        target.spigot().sendMessage(new ComponentBuilder("Received ")
                .color(ChatColor.DARK_GREEN)
                .append("%.2f".formatted(parsed))
                .color(ChatColor.LIGHT_PURPLE)
                .append(" Fairy Dust")
                .color(ChatColor.DARK_PURPLE)
                .append(" from ")
                .color(ChatColor.DARK_GREEN)
                .append(player.getName())
                .color(ChatColor.GREEN)
                .build());

        // Tell the payer they've transferred their balance
        player.spigot().sendMessage(new ComponentBuilder("Sent ")
                .color(ChatColor.DARK_GREEN)
                .append("%.2f".formatted(parsed))
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
}
