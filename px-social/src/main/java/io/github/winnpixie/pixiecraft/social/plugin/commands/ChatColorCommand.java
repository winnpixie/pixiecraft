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
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class ChatColorCommand extends PlayerCommand<PxSocialPlugin> {
    private final BaseComponent colorClearedMessage = new ComponentBuilder("Your chat color has been cleared.")
            .color(ChatColor.DARK_PURPLE)
            .build();

    private final Pattern nonHex = Pattern.compile("[^a-f0-9]", Pattern.CASE_INSENSITIVE);

    public ChatColorCommand(PxSocialPlugin plugin) {
        super("chat-color", plugin);
    }

    @Override
    public boolean execute(Player player, Command command, String label, String[] args) {
        String newColor = "";

        if (args.length > 0) {
            newColor = args[0].toLowerCase();
            if (newColor.indexOf('#') == 0) {
                newColor = newColor.substring(1);
            }

            if (newColor.length() != 6 || nonHex.matcher(newColor).find()) {
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

                player.spigot().sendMessage(new ComponentBuilder("Changing your chat color costed ")
                        .color(ChatColor.GREEN)
                        .append("%.2f %s".formatted(SocialConfig.CHAT_COLOR_PRICE, EconomyConfig.CURRENCY_NAME))
                        .color(ChatColor.LIGHT_PURPLE)
                        .build());
            }
        }

        PDCWrapper<PxSocialPlugin> pdc = new PDCWrapper<>(getPlugin(), player);
        if (!newColor.isBlank()) {
            pdc.setString("chat_color", newColor);

            player.spigot().sendMessage(new ComponentBuilder("Your chat color is now ")
                    .color(ChatColor.DARK_PURPLE)
                    .appendLegacy(TextHelper.fromHexCodes("<#%s>#%1$s".formatted(newColor)))
                    .build());
        } else {
            pdc.remove("chat_color");

            player.spigot().sendMessage(colorClearedMessage);
        }

        return true;
    }
}
