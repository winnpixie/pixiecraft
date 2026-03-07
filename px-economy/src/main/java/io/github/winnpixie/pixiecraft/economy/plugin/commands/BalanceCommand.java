package io.github.winnpixie.pixiecraft.economy.plugin.commands;

import io.github.winnpixie.pixiecraft.commons.CommonWarnings;
import io.github.winnpixie.pixiecraft.commons.commands.PlayerCommand;
import io.github.winnpixie.pixiecraft.economy.api.IUser;
import io.github.winnpixie.pixiecraft.economy.plugin.PxEconomyPlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class BalanceCommand extends PlayerCommand<PxEconomyPlugin> {
    public BalanceCommand(PxEconomyPlugin plugin) {
        super("balance", plugin);
    }

    @Override
    public boolean execute(Player player, Command command, String label, String[] args) {
        IUser user = getPlugin().getUserManager().get(player);
        if (user == null) {
            player.spigot().sendMessage(CommonWarnings.INVALID_TARGET);
            return false;
        }

        player.spigot().sendMessage(new ComponentBuilder("You are carrying ")
                .color(ChatColor.DARK_GREEN)
                .append("%.2f".formatted(user.getWallet().getBalance() / 100.00))
                .color(ChatColor.LIGHT_PURPLE)
                .append(" Fairy Dust")
                .color(ChatColor.DARK_PURPLE)
                .build());
        return true;
    }
}
