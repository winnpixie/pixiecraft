package io.github.winnpixie.pixiecraft.social.plugin.commands;

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

public class NicknameCommand extends PlayerCommand<PxSocialPlugin> {
    private final BaseComponent nickResetMessage = new ComponentBuilder("Your nickname has been cleared.")
            .color(ChatColor.DARK_PURPLE)
            .build();

    public NicknameCommand(PxSocialPlugin plugin) {
        super("nickname", plugin);
    }

    @Override
    public boolean execute(Player player, Command command, String label, String[] args) {
        WrappedPDC<PxSocialPlugin> pdc = new WrappedPDC<>(getPlugin(), player);

        if (args.length > 0) {
            String nickname = TextHelper.format(args[0]);
            player.setDisplayName(nickname);
            pdc.setString("nickname", nickname);

            player.spigot().sendMessage(new ComponentBuilder("Your nickname is now: ")
                    .color(ChatColor.DARK_PURPLE)
                    .append(TextComponent.fromLegacy(String.format("\u00A7r%s", nickname)))
                    .build());
            return true;
        }

        player.setDisplayName(null);
        pdc.remove("nickname");

        player.spigot().sendMessage(nickResetMessage);
        return true;
    }
}
