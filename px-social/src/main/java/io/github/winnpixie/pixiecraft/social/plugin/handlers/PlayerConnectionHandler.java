package io.github.winnpixie.pixiecraft.social.plugin.handlers;

import io.github.winnpixie.pixiecraft.commons.BaseEventHandler;
import io.github.winnpixie.pixiecraft.commons.WrappedPDC;
import io.github.winnpixie.pixiecraft.social.plugin.PxSocialPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerConnectionHandler extends BaseEventHandler<PxSocialPlugin> {
    public PlayerConnectionHandler(PxSocialPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        WrappedPDC<PxSocialPlugin> pdc = new WrappedPDC<>(getPlugin(), player);

        pdc.setString("chat_channel", "global");

        if (pdc.has("nickname")) {
            player.setDisplayName(pdc.getString("nickname"));
        }
    }
}
