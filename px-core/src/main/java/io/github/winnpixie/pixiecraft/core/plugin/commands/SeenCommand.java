package io.github.winnpixie.pixiecraft.core.plugin.commands;

import io.github.winnpixie.pixiecraft.commons.WarningMessages;
import io.github.winnpixie.pixiecraft.commons.commands.BaseCommand;
import io.github.winnpixie.pixiecraft.core.plugin.PxCorePlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class SeenCommand extends BaseCommand<PxCorePlugin> {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy '@' HH:mm:ss a")
            .withZone(ZoneId.systemDefault());

    public SeenCommand(PxCorePlugin plugin) {
        super("seen", plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) {
            sender.spigot().sendMessage(WarningMessages.MISSING_PARAMETERS);
            return false;
        }

        OfflinePlayer player = getPlugin().getServer().getPlayerExact(args[0]);
        if (player != null) {
            showInstant(sender, player, Instant.now());
            return true;
        }

        player = getPlugin().getServer().getOfflinePlayer(args[0]);
        if (!player.hasPlayedBefore()) {
            sender.spigot().sendMessage(WarningMessages.INVALID_TARGET);
            return false;
        }

        showInstant(sender, player, Instant.ofEpochMilli(player.getLastPlayed()));
        return true;
    }

    private void showInstant(CommandSender sender, OfflinePlayer player, Instant instant) {
        sender.spigot().sendMessage(new ComponentBuilder(player.getName())
                .color(ChatColor.WHITE)
                .append(" was last seen on ")
                .color(ChatColor.DARK_PURPLE)
                .append(dateTimeFormatter.format(instant))
                .color(ChatColor.LIGHT_PURPLE).build());
    }
}
