package io.github.winnpixie.pixiecraft.social.plugin.commands;

import io.github.winnpixie.pixiecraft.commons.WrappedPDC;
import io.github.winnpixie.pixiecraft.commons.commands.PlayerCommand;
import io.github.winnpixie.pixiecraft.social.plugin.PxSocialPlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class ChannelCommand extends PlayerCommand<PxSocialPlugin> {
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
        WrappedPDC<PxSocialPlugin> pdc = new WrappedPDC<>(getPlugin(), player);

        if (args.length > 0) {
            String channel = args[0].toLowerCase();
            pdc.setString("chat_channel", channel);

            player.spigot().sendMessage(new ComponentBuilder("Your chat color has been set: ")
                    .color(ChatColor.DARK_PURPLE)
                    .append(String.format("#%s", channel))
                    .color(ChatColor.LIGHT_PURPLE)
                    .build());
            return true;
        }

        pdc.setString("chat_channel", "global");

        player.spigot().sendMessage(resetChannelMessage);
        return true;
    }
}
