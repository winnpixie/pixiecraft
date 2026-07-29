package io.github.winnpixie.pixiecraft.social.plugin.handlers;

import io.github.winnpixie.pixiecraft.commons.BaseEventHandler;
import io.github.winnpixie.pixiecraft.commons.PDCWrapper;
import io.github.winnpixie.pixiecraft.social.plugin.PxSocialPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionHandler extends BaseEventHandler<PxSocialPlugin> {
    public PlayerConnectionHandler(PxSocialPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PDCWrapper<PxSocialPlugin> pdc = new PDCWrapper<>(getPlugin(), player);

        pdc.setString("chat_channel", "global");

        if (pdc.has("nickname")) {
            player.setDisplayName(pdc.getString("nickname"));
        }

        getPlugin().getBubbleTracker().add(player);
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        getPlugin().getBubbleTracker().remove(event.getPlayer());
    }
}
