package io.github.winnpixie.pixiecraft.social.plugin.handlers;

import io.github.winnpixie.pixiecraft.commons.BaseEventHandler;
import io.github.winnpixie.pixiecraft.commons.PDCWrapper;
import io.github.winnpixie.pixiecraft.commons.TextHelper;
import io.github.winnpixie.pixiecraft.social.plugin.PxSocialPlugin;
import io.github.winnpixie.pixiecraft.social.plugin.bubbles.ChatBubble;
import io.github.winnpixie.pixiecraft.social.plugin.utilities.MessageHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Set;

public class PlayerChatHandler extends BaseEventHandler<PxSocialPlugin> {
    private static final String GLOBAL_CHANNEL = "global";

    public PlayerChatHandler(PxSocialPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    private void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        PDCWrapper<PxSocialPlugin> pdc = new PDCWrapper<>(getPlugin(), player);

        String chatChannel = pdc.getString("chat_channel");
        if (chatChannel == null) {
            chatChannel = GLOBAL_CHANNEL;
        }

        if (!GLOBAL_CHANNEL.equals(chatChannel)) {
            final String selectedChannel = chatChannel;
            Set<Player> recipients = event.getRecipients();
            recipients.removeIf(recipient -> {
                PDCWrapper<PxSocialPlugin> recipientPdc = new PDCWrapper<>(getPlugin(), recipient);
                String recipientChannel = recipientPdc.getString("chat_channel");
                if (recipientChannel == null) {
                    recipientChannel = GLOBAL_CHANNEL;
                }

                return !recipientChannel.equals(selectedChannel);
            });
        }

        message = MessageHelper.transform(pdc, message);

        if (pdc.has("chat_color")) {
            message = "%s%s".formatted(TextHelper.fromHexCodes("<#%s>".formatted(pdc.getString("chat_color"))), message);
        }

        event.setMessage(message);

        char channelColor = GLOBAL_CHANNEL.equals(chatChannel) ? '8' : '7';
        String fmt = "\u00A7%c[#%s] \u00A7r".formatted(channelColor, chatChannel);
        if (pdc.has("nickname")) {
            fmt += "\"%1$s\u00A7r\"";
        } else {
            fmt += "%1$s";
        }
        fmt += "\u00A7r: %2$s"; // Reset probably isn't necessary here, whatever
        event.setFormat(fmt);

        // Handle bubbles
        if (GLOBAL_CHANNEL.equals(chatChannel)) {
            ChatBubble bubble = getPlugin().getBubbles().get(player);
            if (bubble == null) {
                return; // safe, for now?
            }

            bubble.display(message);
        }
    }
}
