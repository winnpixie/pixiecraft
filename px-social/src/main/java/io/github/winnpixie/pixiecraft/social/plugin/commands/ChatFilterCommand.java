package io.github.winnpixie.pixiecraft.social.plugin.commands;

import io.github.winnpixie.pixiecraft.commons.CommonWarnings;
import io.github.winnpixie.pixiecraft.commons.WrappedPDC;
import io.github.winnpixie.pixiecraft.commons.commands.PlayerCommand;
import io.github.winnpixie.pixiecraft.social.plugin.PxSocialPlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class ChatFilterCommand extends PlayerCommand<PxSocialPlugin> {
    public ChatFilterCommand(PxSocialPlugin plugin) {
        super("chatfilter", plugin);
    }

    @Override
    public boolean execute(Player player, Command command, String label, String[] args) {
        if (args.length < 1) {
            player.spigot().sendMessage(CommonWarnings.NOT_ENOUGH_ARGS);
            return false;
        }

        WrappedPDC<PxSocialPlugin> pdc = new WrappedPDC<>(getPlugin(), player);

        return switch (args[0].toLowerCase()) {
            case "uwu" -> {
                boolean uwu = pdc.has("uwu_filter") && pdc.getBoolean("uwu_filter");
                pdc.setBoolean("uwu_filter", !uwu);

                player.spigot().sendMessage(new ComponentBuilder("UwU Filter: ")
                        .color(ChatColor.DARK_PURPLE)
                        .append(String.valueOf(!uwu))
                        .color(ChatColor.LIGHT_PURPLE)
                        .build());
                yield true;
            }
            case "leet" -> {
                boolean leet = pdc.has("leet_filter") && pdc.getBoolean("leet_filter");
                pdc.setBoolean("leet_filter", !leet);

                player.spigot().sendMessage(new ComponentBuilder("L33T Filter: ")
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
