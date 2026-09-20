package io.github.winnpixie.pixiecraft.social.plugin.commands;

import io.github.winnpixie.pixiecraft.commons.PDCWrapper;
import io.github.winnpixie.pixiecraft.commons.WarningMessages;
import io.github.winnpixie.pixiecraft.commons.commands.PlayerCommand;
import io.github.winnpixie.pixiecraft.social.plugin.PxSocialPlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class ChannelCommand extends PlayerCommand<PxSocialPlugin> {
    private final Pattern illegalChannelChars = Pattern.compile("\\W", Pattern.CASE_INSENSITIVE);

    public ChannelCommand(PxSocialPlugin plugin) {
        super("channel", plugin);
    }

    @Override
    public boolean execute(Player player, Command command, String label, String[] args) {
        PDCWrapper<PxSocialPlugin> pdc = new PDCWrapper<>(getPlugin(), player);

        String newChannel = "global";

        if (args.length > 0) {
            newChannel = args[0].toLowerCase();
            if (illegalChannelChars.matcher(newChannel).matches()) {
                player.spigot().sendMessage(WarningMessages.WRONG_ARGUMENT_TYPE);
                return false;
            }
        }

        pdc.setString("chat_channel", newChannel);

        player.spigot().sendMessage(new ComponentBuilder("Your chat channel has been set: ")
                .color(ChatColor.DARK_PURPLE)
                .append("#%s".formatted(newChannel))
                .color(ChatColor.LIGHT_PURPLE)
                .build());
        return true;
    }
}
