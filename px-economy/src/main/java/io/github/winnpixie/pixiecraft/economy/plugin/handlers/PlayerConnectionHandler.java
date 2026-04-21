package io.github.winnpixie.pixiecraft.economy.plugin.handlers;

import io.github.winnpixie.pixiecraft.commons.BaseEventHandler;
import io.github.winnpixie.pixiecraft.economy.api.IUser;
import io.github.winnpixie.pixiecraft.economy.plugin.EconomyConfig;
import io.github.winnpixie.pixiecraft.economy.plugin.PxEconomyPlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionHandler extends BaseEventHandler<PxEconomyPlugin> {
    public PlayerConnectionHandler(PxEconomyPlugin plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        IUser user = getPlugin().getUserManager().load(player);
        if (getPlugin().getCentralBank().find(user) == null) {
            getPlugin().getCentralBank().register(user);
        }

        double firstJoinBonus = EconomyConfig.FIRST_JOIN_BONUS;
        if (!player.hasPlayedBefore()
                && firstJoinBonus > 0.0) {
            user.getWallet().earn((long) (firstJoinBonus * 100.00));

            player.spigot().sendMessage(new ComponentBuilder("You've earned ")
                    .color(ChatColor.DARK_GREEN)
                    .append("%.2f".formatted(firstJoinBonus))
                    .color(ChatColor.LIGHT_PURPLE)
                    .append(" %s".formatted(EconomyConfig.CURRENCY_NAME))
                    .color(ChatColor.DARK_PURPLE)
                    .append(" for joining for your first time!")
                    .color(ChatColor.DARK_GREEN)
                    .append(" Thank you for being here! :)")
                    .color(ChatColor.GREEN)
                    .build());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        IUser user = getPlugin().getUserManager().get(player);

        getPlugin().getUserManager().save(user);
        getPlugin().getUserManager().remove(user);
    }
}
