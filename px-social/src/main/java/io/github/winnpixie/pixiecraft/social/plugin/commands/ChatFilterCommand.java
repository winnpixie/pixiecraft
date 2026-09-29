package io.github.winnpixie.pixiecraft.social.plugin.commands;

import io.github.winnpixie.pixiecraft.commons.PDCWrapper;
import io.github.winnpixie.pixiecraft.commons.WarningMessages;
import io.github.winnpixie.pixiecraft.commons.commands.PlayerCommand;
import io.github.winnpixie.pixiecraft.social.plugin.PxSocialPlugin;
import io.github.winnpixie.pixiecraft.social.plugin.utilities.MessageHelper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class ChatFilterCommand extends PlayerCommand<PxSocialPlugin> {
    public ChatFilterCommand(PxSocialPlugin plugin) {
        super("chat-filter", plugin);
    }

    @Override
    public boolean execute(Player player, Command command, String label, String[] args) {
        if (args.length < 1) {
            player.spigot().sendMessage(WarningMessages.MISSING_PARAMETERS);
            return false;
        }

        PDCWrapper<PxSocialPlugin> pdc = new PDCWrapper<>(getPlugin(), player);

        return switch (args[0].toLowerCase()) {
            case "uwu" -> {
                boolean uwu = pdc.has(MessageHelper.UWU_TAG)
                        && pdc.getBoolean(MessageHelper.UWU_TAG);
                pdc.setBoolean(MessageHelper.UWU_TAG, !uwu);

                player.spigot().sendMessage(new ComponentBuilder("UwU: ")
                        .color(ChatColor.DARK_PURPLE)
                        .append(String.valueOf(!uwu))
                        .color(ChatColor.LIGHT_PURPLE)
                        .build());
                yield true;
            }
            case "leet" -> {
                boolean leet = pdc.has(MessageHelper.LEET_TAG)
                        && pdc.getBoolean(MessageHelper.LEET_TAG);
                pdc.setBoolean(MessageHelper.LEET_TAG, !leet);

                player.spigot().sendMessage(new ComponentBuilder("1337: ")
                        .color(ChatColor.DARK_PURPLE)
                        .append(String.valueOf(!leet))
                        .color(ChatColor.LIGHT_PURPLE)
                        .build());
                yield true;
            }
            default -> false;
        };
    }
}
