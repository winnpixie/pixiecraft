package io.github.winnpixie.pixiecraft.social.plugin.handlers;

import io.github.winnpixie.pixiecraft.commons.BaseEventHandler;
import io.github.winnpixie.pixiecraft.commons.PDCWrapper;
import io.github.winnpixie.pixiecraft.commons.TextHelper;
import io.github.winnpixie.pixiecraft.social.plugin.PxSocialPlugin;
import io.github.winnpixie.pixiecraft.social.plugin.utilities.MessageHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Set;

public class PlayerChatHandler extends BaseEventHandler<PxSocialPlugin> {

    public PlayerChatHandler(PxSocialPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    private void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        PDCWrapper<PxSocialPlugin> pdc = new PDCWrapper<>(getPlugin(), player);

        String channel = pdc.getString("chat_channel");
        if (!channel.equals("global")) {
            Set<Player> recipients = event.getRecipients();
            recipients.removeIf(recipient -> {
                PDCWrapper<PxSocialPlugin> recipientPdc = new PDCWrapper<>(getPlugin(), recipient);
                return !recipientPdc.getString("chat_channel").equals(channel);
            });
        }

        if (pdc.has("uwu_filter") && pdc.getBoolean("uwu_filter")) {
            message = MessageHelper.uwuify(message);
        }

        if (pdc.has("leet_filter") && pdc.getBoolean("leet_filter")) {
            message = MessageHelper.hack(message);
        }

        if (pdc.has("chat_color")) {
            message = "%s%s".formatted(TextHelper.convertHexColors("<#%s>".formatted(pdc.getString("chat_color"))), message);
        }

        event.setMessage(message);
        char channelColor = channel.equals("global") ? '8' : '7';
        event.setFormat("\u00A7%c[#%s] \u00A7r<%%1$s\u00A7r> %%2$s".formatted(channelColor, channel));
    }
}
