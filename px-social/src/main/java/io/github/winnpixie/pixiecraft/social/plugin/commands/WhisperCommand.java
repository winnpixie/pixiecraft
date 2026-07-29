package io.github.winnpixie.pixiecraft.social.plugin.commands;

import io.github.winnpixie.pixiecraft.commons.WarningMessages;
import io.github.winnpixie.pixiecraft.commons.commands.PlayerCommand;
import io.github.winnpixie.pixiecraft.social.plugin.PxSocialPlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class WhisperCommand extends PlayerCommand<PxSocialPlugin> {
    public WhisperCommand(PxSocialPlugin plugin) {
        super("whisper", plugin);
    }

    @Override
    public boolean execute(Player player, Command command, String label, String[] args) {
        if (args.length < 2) {
            player.spigot().sendMessage(WarningMessages.MISSING_PARAMETERS);
            return false;
        }

        Player receiver = getPlugin().getServer().getPlayerExact(args[0]);
        if (receiver == null) {
            player.spigot().sendMessage(WarningMessages.INVALID_TARGET);
            return false;
        }

        String[] arguments = new String[args.length - 1];
        System.arraycopy(args, 1, arguments, 0, arguments.length);
        String message = String.join(" ", arguments);

        player.spigot().sendMessage(new ComponentBuilder("You")
                .color(ChatColor.LIGHT_PURPLE)
                .append(" tell ")
                .color(ChatColor.DARK_PURPLE)
                .append(receiver.getName())
                .color(ChatColor.WHITE)
                .append(": ")
                .color(ChatColor.DARK_PURPLE)
                .append(message)
                .color(ChatColor.WHITE)
                .build());

        receiver.spigot().sendMessage(new ComponentBuilder(player.getName())
                .color(ChatColor.WHITE)
                .append(" told ")
                .color(ChatColor.DARK_PURPLE)
                .append("you")
                .color(ChatColor.LIGHT_PURPLE)
                .append(": ")
                .color(ChatColor.DARK_PURPLE)
                .append(message)
                .color(ChatColor.WHITE)
                .build());
        return true;
    }
}
