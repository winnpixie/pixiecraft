package io.github.winnpixie.pixiecraft.social.plugin.commands;

import io.github.winnpixie.pixiecraft.commons.CommonWarnings;
import io.github.winnpixie.pixiecraft.commons.WrappedPDC;
import io.github.winnpixie.pixiecraft.commons.TextHelper;
import io.github.winnpixie.pixiecraft.commons.commands.PlayerCommand;
import io.github.winnpixie.pixiecraft.social.plugin.PxSocialPlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class ColorCommand extends PlayerCommand<PxSocialPlugin> {
    private final BaseComponent colorClearedMessage = new ComponentBuilder("Chat color has been cleared.")
            .color(ChatColor.DARK_PURPLE)
            .build();

    private final Pattern nonHex = Pattern.compile("[^a-f0-9]", Pattern.CASE_INSENSITIVE);

    public ColorCommand(PxSocialPlugin plugin) {
        super("color", plugin);
    }

    @Override
    public boolean execute(Player player, Command command, String label, String[] args) {
        WrappedPDC<PxSocialPlugin> pdc = new WrappedPDC<>(getPlugin(), player);

        if (args.length > 0) {
            String color = args[0].toLowerCase();

            if (color.length() != 6 || nonHex.matcher(color).find()) {
                player.spigot().sendMessage(CommonWarnings.INVALID_ARG_TYPE);
                return false;
            }

            pdc.setString("chat_color", color);

            player.spigot().sendMessage(new ComponentBuilder("Your chat color has been set: ")
                    .color(ChatColor.DARK_PURPLE)
                    .append(TextComponent.fromLegacy(TextHelper.convertHexColors(String.format("<#%s>#%1$s", color))))
                    .build());
            return true;
        }

        pdc.remove("chat_color");

        player.spigot().sendMessage(colorClearedMessage);
        return true;
    }
}
