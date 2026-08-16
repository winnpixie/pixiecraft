package io.github.winnpixie.pixiecraft.mature.plugin.commands;

import io.github.winnpixie.pixiecraft.commons.WarningMessages;
import io.github.winnpixie.pixiecraft.commons.MathHelper;
import io.github.winnpixie.pixiecraft.commons.commands.PlayerCommand;
import io.github.winnpixie.pixiecraft.economy.api.IUser;
import io.github.winnpixie.pixiecraft.economy.api.IWallet;
import io.github.winnpixie.pixiecraft.economy.plugin.EconomyWarnings;
import io.github.winnpixie.pixiecraft.economy.plugin.UnitConverter;
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
            player.spigot().sendMessage(WarningMessages.MISSING_PARAMETERS);
            return false;
        }

        String choice = args[0];
        if (!choice.equalsIgnoreCase("heads")
                && !choice.equalsIgnoreCase("tails")) {
            player.spigot().sendMessage(WarningMessages.WRONG_ARGUMENT_TYPE);
            return false;
        }

        String wager = args[1];
        if (!UnitConverter.isUnit(wager)) {
            player.spigot().sendMessage(WarningMessages.WRONG_ARGUMENT_TYPE);
            return false;
        }

        IUser user = getPlugin().getEconomy().getUserManager().get(player);
        IWallet wallet = user.getWallet();

        long amount = UnitConverter.valueOf(wager);
        if (!wallet.spend(amount)) {
            player.spigot().sendMessage(EconomyWarnings.INSUFFICIENT_WALLET_FUNDS);
            return false;
        }

        String flip = MathHelper.randomInt(0, 100) < 50 ?
                "Heads" : "Tails";

        player.spigot().sendMessage(new ComponentBuilder("The coin landed on... ")
                .color(ChatColor.DARK_PURPLE)
                .append(flip)
                .color(ChatColor.LIGHT_PURPLE)
                .build());

        if (choice.equalsIgnoreCase(flip)) {
            player.spigot().sendMessage(new ComponentBuilder("You won ")
                    .color(ChatColor.GREEN)
                    .append(UnitConverter.toString(amount))
                    .color(ChatColor.LIGHT_PURPLE)
                    .append(" Fairy Dust")
                    .color(ChatColor.DARK_PURPLE)
                    .build());
            wallet.earn(amount * 2L);
        } else {
            player.spigot().sendMessage(new ComponentBuilder("You lost ")
                    .color(ChatColor.RED)
                    .append(UnitConverter.toString(amount))
                    .color(ChatColor.LIGHT_PURPLE)
                    .append(" Fairy Dust")
                    .color(ChatColor.DARK_PURPLE)
                    .build());
        }
        return true;
    }
}
