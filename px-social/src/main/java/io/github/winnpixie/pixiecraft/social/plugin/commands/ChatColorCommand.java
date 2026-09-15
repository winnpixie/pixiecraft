package io.github.winnpixie.pixiecraft.social.plugin.commands;

import io.github.winnpixie.pixiecraft.commons.PDCWrapper;
import io.github.winnpixie.pixiecraft.commons.TextHelper;
import io.github.winnpixie.pixiecraft.commons.WarningMessages;
import io.github.winnpixie.pixiecraft.commons.commands.PlayerCommand;
import io.github.winnpixie.pixiecraft.economy.api.IUser;
import io.github.winnpixie.pixiecraft.economy.plugin.EconomyConfig;
import io.github.winnpixie.pixiecraft.economy.plugin.EconomyWarnings;
import io.github.winnpixie.pixiecraft.social.plugin.PxSocialPlugin;
import io.github.winnpixie.pixiecraft.social.plugin.SocialConfig;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class ChatColorCommand extends PlayerCommand<PxSocialPlugin> {
    private final BaseComponent colorClearedMessage = new ComponentBuilder("Chat color has been cleared.")
            .color(ChatColor.DARK_PURPLE)
            .build();

    private final Pattern nonHex = Pattern.compile("[^a-f0-9]", Pattern.CASE_INSENSITIVE);

    public ChatColorCommand(PxSocialPlugin plugin) {
        super("chat-color", plugin);
    }

    @Override
    public boolean execute(Player player, Command command, String label, String[] args) {
        PDCWrapper<PxSocialPlugin> pdc = new PDCWrapper<>(getPlugin(), player);

        if (args.length > 0) {
            String color = args[0].toLowerCase();

            if (color.length() != 6 || nonHex.matcher(color).find()) {
                player.spigot().sendMessage(WarningMessages.WRONG_ARGUMENT_TYPE);
                return false;
            }

            if (SocialConfig.CHAT_COLOR_PRICE > 0.0) {
                IUser user = getPlugin().getEconomy().getUserManager().get(player);
                if (!user.getWallet().spend((long) (SocialConfig.CHAT_COLOR_PRICE * 100.00))) {
                    player.spigot().sendMessage(EconomyWarnings.INSUFFICIENT_WALLET_FUNDS);

                    player.spigot().sendMessage(WarningMessages.custom("Changing your chat color costs %.2f %s"
                            .formatted(SocialConfig.CHAT_COLOR_PRICE, EconomyConfig.CURRENCY_NAME)));
                    return false;
                }

                player.spigot().sendMessage(TextComponent.fromLegacy(TextHelper.formatted(
                        "<green>Changing your chat color costed you <magenta>%.2f %s"
                                .formatted(SocialConfig.CHAT_COLOR_PRICE, EconomyConfig.CURRENCY_NAME))));
            }

            pdc.setString("chat_color", color);

            player.spigot().sendMessage(new ComponentBuilder("Your chat color has been set: ")
                    .color(ChatColor.DARK_PURPLE)
                    .append(TextComponent.fromLegacy(TextHelper.fromHexCodes("<#%s>#%1$s".formatted(color))))
                    .build());
            return true;
        }

        pdc.remove("chat_color");

        player.spigot().sendMessage(colorClearedMessage);
        return true;
    }
}
