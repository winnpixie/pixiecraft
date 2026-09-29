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

public class NicknameCommand extends PlayerCommand<PxSocialPlugin> {
    private final BaseComponent blankWarning = new ComponentBuilder("Your nickname cannot be blank!")
            .color(ChatColor.RED)
            .build();
    private final BaseComponent resetMessage = new ComponentBuilder("Your nickname has been cleared.")
            .color(ChatColor.DARK_PURPLE)
            .build();

    public NicknameCommand(PxSocialPlugin plugin) {
        super("nickname", plugin);
    }

    @Override
    public boolean execute(Player player, Command command, String label, String[] args) {
        String newNickname = "";

        if (args.length > 0) {
            newNickname = TextHelper.formatted(args[0]);
            if (ChatColor.stripColor(newNickname).isBlank()) {
                player.spigot().sendMessage(blankWarning);
                return false;
            }

            if (SocialConfig.NICKNAME_PRICE > 0.0) {
                IUser user = getPlugin().getEconomy().getUserManager().get(player);
                if (!user.getWallet().spend((long) (SocialConfig.NICKNAME_PRICE * 100.00))) {
                    player.spigot().sendMessage(EconomyWarnings.INSUFFICIENT_WALLET_FUNDS);

                    player.spigot().sendMessage(WarningMessages.custom("Changing your nickname costs %.2f %s"
                            .formatted(SocialConfig.NICKNAME_PRICE, EconomyConfig.CURRENCY_NAME)));
                    return false;
                }

                player.spigot().sendMessage(new ComponentBuilder("Changing your nickname costed ")
                        .color(ChatColor.GREEN)
                        .append("%.2f %s".formatted(SocialConfig.NICKNAME_PRICE, EconomyConfig.CURRENCY_NAME))
                        .color(ChatColor.LIGHT_PURPLE)
                        .build());
            }
        }

        PDCWrapper<PxSocialPlugin> pdc = new PDCWrapper<>(getPlugin(), player);
        if (!newNickname.isBlank()) {
            player.setDisplayName(newNickname);
            pdc.setString("nickname", newNickname);

            player.spigot().sendMessage(new ComponentBuilder("Your nickname is now ")
                    .color(ChatColor.DARK_PURPLE)
                    .appendLegacy("\u00A7r%s".formatted(newNickname))
                    .build());
        } else {
            player.setDisplayName(null);
            pdc.remove("nickname");

            player.spigot().sendMessage(resetMessage);
        }

        return true;
    }
}
