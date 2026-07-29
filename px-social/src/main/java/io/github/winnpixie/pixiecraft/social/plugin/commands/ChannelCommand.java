package io.github.winnpixie.pixiecraft.social.plugin.commands;

import io.github.winnpixie.pixiecraft.commons.WarningMessages;
import io.github.winnpixie.pixiecraft.commons.PDCWrapper;
import io.github.winnpixie.pixiecraft.commons.commands.PlayerCommand;
import io.github.winnpixie.pixiecraft.social.plugin.PxSocialPlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class ChannelCommand extends PlayerCommand<PxSocialPlugin> {
    private final Pattern illegalChannelChars = Pattern.compile("\\W", Pattern.CASE_INSENSITIVE);

    private final BaseComponent resetChannelMessage = new ComponentBuilder("Your channel has been set to: ")
            .color(ChatColor.DARK_PURPLE)
            .append("#global")
            .color(ChatColor.LIGHT_PURPLE)
            .build();

    public ChannelCommand(PxSocialPlugin plugin) {
        super("channel", plugin);
    }

    @Override
    public boolean execute(Player player, Command command, String label, String[] args) {
        PDCWrapper<PxSocialPlugin> pdc = new PDCWrapper<>(getPlugin(), player);

        if (args.length > 0) {
            String channel = args[0].toLowerCase();
            if (illegalChannelChars.matcher(channel).matches()) {
                player.spigot().sendMessage(WarningMessages.WRONG_ARGUMENT_TYPE);
                return false;
            }

            pdc.setString("chat_channel", channel);

            player.spigot().sendMessage(new ComponentBuilder("Your chat color has been set: ")
                    .color(ChatColor.DARK_PURPLE)
                    .append("#%s".formatted(channel))
                    .color(ChatColor.LIGHT_PURPLE)
                    .build());
            return true;
        }

        pdc.setString("chat_channel", "global");

        player.spigot().sendMessage(resetChannelMessage);
        return true;
    }
}
