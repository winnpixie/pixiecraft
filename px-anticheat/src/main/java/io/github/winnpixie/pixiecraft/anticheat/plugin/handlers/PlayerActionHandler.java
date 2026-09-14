package io.github.winnpixie.pixiecraft.anticheat.plugin.handlers;

import io.github.winnpixie.pixiecraft.anticheat.plugin.CheckConfig;
import io.github.winnpixie.pixiecraft.anticheat.plugin.PxAntiCheatPlugin;
import io.github.winnpixie.pixiecraft.anticheat.plugin.users.ActiveUser;
import io.github.winnpixie.pixiecraft.commons.BaseEventHandler;
import io.github.winnpixie.pixiecraft.commons.TextHelper;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerActionHandler extends BaseEventHandler<PxAntiCheatPlugin> {
    public PlayerActionHandler(PxAntiCheatPlugin plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        ActiveUser user = getPlugin().getTracker().getActive(player);

        long now = System.currentTimeMillis();
        if (now - user.getLastMessageTime() < CheckConfig.CHAT_DELAY) {
            player.spigot().sendMessage(TextComponent.fromLegacy(TextHelper.formatted(CheckConfig.CHAT_DELAY_WARNING)));
            event.setCancelled(true);
            return;
        }

        user.setLastMessageTime(now);

        String message = event.getMessage();
        if (message.equals(user.getLastSentMessage())) {
            player.spigot().sendMessage(TextComponent.fromLegacy(TextHelper.formatted(CheckConfig.REPEAT_CHAT_WARNING)));
            event.setCancelled(true);
            return;
        }

        user.setLastSentMessage(message);
    }
}
