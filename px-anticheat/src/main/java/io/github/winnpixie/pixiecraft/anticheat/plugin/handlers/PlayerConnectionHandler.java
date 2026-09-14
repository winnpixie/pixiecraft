package io.github.winnpixie.pixiecraft.anticheat.plugin.handlers;

import io.github.winnpixie.pixiecraft.anticheat.plugin.CheckConfig;
import io.github.winnpixie.pixiecraft.anticheat.plugin.PxAntiCheatPlugin;
import io.github.winnpixie.pixiecraft.anticheat.plugin.users.PassiveUser;
import io.github.winnpixie.pixiecraft.commons.BaseEventHandler;
import io.github.winnpixie.pixiecraft.commons.TextHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionHandler extends BaseEventHandler<PxAntiCheatPlugin> {
    public PlayerConnectionHandler(PxAntiCheatPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PassiveUser passive = getPlugin().getTracker().getPassive(player);
        if (passive == null) {
            passive = getPlugin().getTracker().addPassive(player);
        }

        if (System.nanoTime() - passive.getLastQuitTime() / 1000000 < CheckConfig.JOIN_DELAY) {
            player.kickPlayer(TextHelper.formatted(CheckConfig.JOIN_DELAY_WARNING));
            return;
        }

        getPlugin().getTracker().addActive(player);
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        getPlugin().getTracker().removeActive(player);

        PassiveUser passive = getPlugin().getTracker().getPassive(player.getUniqueId());
        if (passive == null) {
            return;
        }

        passive.setLastQuitTime(System.nanoTime());
    }
}
