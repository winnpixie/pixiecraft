package io.github.winnpixie.pixiecraft.core.plugin.commands;

import io.github.winnpixie.pixiecraft.commons.CommonWarnings;
import io.github.winnpixie.pixiecraft.commons.commands.BaseCommand;
import io.github.winnpixie.pixiecraft.core.plugin.PxCorePlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PingCommand extends BaseCommand<PxCorePlugin> {
    public PingCommand(PxCorePlugin plugin) {
        super("ping", plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) {
            if (!(sender instanceof Player player)) {
                sender.spigot().sendMessage(CommonWarnings.PLAYERS_ONLY);
                return false;
            }

            sender.spigot().sendMessage(new ComponentBuilder("Your")
                    .color(ChatColor.LIGHT_PURPLE)
                    .append(" ping is ")
                    .color(ChatColor.DARK_PURPLE)
                    .append("%dms".formatted(player.getPing()))
                    .color(ChatColor.LIGHT_PURPLE)
                    .italic(true)
                    .build());
            return true;
        }

        Player player = getPlugin().getServer().getPlayerExact(args[0]);
        if (player == null) {
            sender.spigot().sendMessage(CommonWarnings.INVALID_TARGET);
            return false;
        }

        sender.spigot().sendMessage(new ComponentBuilder(player.getName())
                .color(ChatColor.WHITE)
                .append("'s ping is ")
                .color(ChatColor.DARK_PURPLE)
                .append("%dms".formatted(player.getPing()))
                .color(ChatColor.LIGHT_PURPLE)
                .italic(true)
                .build());
        return true;
    }
}
