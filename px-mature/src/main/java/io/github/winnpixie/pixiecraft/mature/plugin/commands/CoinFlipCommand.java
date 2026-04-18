package io.github.winnpixie.pixiecraft.mature.plugin.commands;

import io.github.winnpixie.pixiecraft.commons.CommonWarnings;
import io.github.winnpixie.pixiecraft.commons.MathHelper;
import io.github.winnpixie.pixiecraft.commons.commands.PlayerCommand;
import io.github.winnpixie.pixiecraft.economy.api.IUser;
import io.github.winnpixie.pixiecraft.economy.api.IWallet;
import io.github.winnpixie.pixiecraft.economy.plugin.EconomyWarnings;
import io.github.winnpixie.pixiecraft.economy.plugin.PxEconomyPlugin;
import io.github.winnpixie.pixiecraft.mature.plugin.PxMaturePlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CoinFlipCommand extends PlayerCommand<PxMaturePlugin> {
    public CoinFlipCommand(PxMaturePlugin plugin) {
        super("coin-flip", plugin);
    }

    @Override
    public boolean execute(Player player, Command command, String label, String[] args) {
        if (args.length < 2) {
            player.spigot().sendMessage(CommonWarnings.NOT_ENOUGH_ARGS);
            return false;
        }

        String choice = args[0];
        if (!choice.equalsIgnoreCase("heads")
                && !choice.equalsIgnoreCase("tails")) {
            player.spigot().sendMessage(CommonWarnings.INVALID_ARG_TYPE);
            return false;
        }

        String wager = args[1];
        if (!MathHelper.isDouble(wager)) {
            player.spigot().sendMessage(CommonWarnings.INVALID_ARG_TYPE);
            return false;
        }

        IUser user = PxEconomyPlugin.getInstance().getUserManager().get(player);
        IWallet wallet = user.getWallet();

        double parsed = Double.parseDouble(wager);
        long amount = (long) (parsed * 100.00);
        if (!wallet.spend(amount)) {
            player.spigot().sendMessage(EconomyWarnings.INSUFFICIENT_WALLET_FUNDS);
            return false;
        }

        String flip = MathHelper.randomInt(0, 100) < 50 ?
                "Heads" : "Tails";

        player.spigot().sendMessage(new ComponentBuilder("The coin landed on... ")
                .color(ChatColor.GRAY)
                .append(flip)
                .color(ChatColor.WHITE)
                .build());

        if (choice.equalsIgnoreCase(flip)) {
            player.spigot().sendMessage(new ComponentBuilder("You won ")
                    .color(ChatColor.GREEN)
                    .append("%.2f".formatted(parsed))
                    .color(ChatColor.LIGHT_PURPLE)
                    .append(" Fairy Dust")
                    .color(ChatColor.DARK_PURPLE)
                    .build());
            wallet.earn(amount * 2L);
        } else {
            player.spigot().sendMessage(new ComponentBuilder("You lost ")
                    .color(ChatColor.RED)
                    .append("%.2f".formatted(parsed))
                    .color(ChatColor.LIGHT_PURPLE)
                    .append(" Fairy Dust")
                    .color(ChatColor.DARK_PURPLE)
                    .build());
        }

        return true;
    }
}
