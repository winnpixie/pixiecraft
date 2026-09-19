package io.github.winnpixie.pixiecraft.core.plugin.handlers;

import io.github.winnpixie.pixiecraft.commons.BaseEventHandler;
import io.github.winnpixie.pixiecraft.commons.MathHelper;
import io.github.winnpixie.pixiecraft.commons.TextHelper;
import io.github.winnpixie.pixiecraft.core.plugin.CoreConfig;
import io.github.winnpixie.pixiecraft.core.plugin.PxCorePlugin;
import io.github.winnpixie.pixiecraft.economy.api.IUser;
import io.github.winnpixie.pixiecraft.economy.plugin.EconomyConfig;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;

public class ConnectionHandler extends BaseEventHandler<PxCorePlugin> {
    public ConnectionHandler(PxCorePlugin plugin) {
        super(plugin);
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(TextHelper.formatted(CoreConfig.JOIN_MESSAGE
                .replace("{PLAYER_NAME}", player.getName())
        ));

        if (player.hasPlayedBefore()) {
            return;
        }

        if (!CoreConfig.WELCOME_MESSAGES.isEmpty()) {
            for (String message : CoreConfig.WELCOME_MESSAGES) {
                player.spigot().sendMessage(TextComponent.fromLegacy(TextHelper.formatted(message)));
            }
        }

        double newbiePay = CoreConfig.NEWBIE_PAY;
        if (newbiePay > 0.0) {
            IUser user = getPlugin().getEconomy().getUserManager().get(player);
            user.getWallet().earn((long) (newbiePay * 100.00));

            player.spigot().sendMessage(new ComponentBuilder("You received ")
                    .color(ChatColor.DARK_GREEN)
                    .append("%.2f".formatted(newbiePay))
                    .color(ChatColor.LIGHT_PURPLE)
                    .append(" %s".formatted(EconomyConfig.CURRENCY_NAME))
                    .color(ChatColor.DARK_PURPLE)
                    .append(" as a welcoming gift!")
                    .color(ChatColor.DARK_GREEN)
                    .append(" <3")
                    .color(ChatColor.RED)
                    .build());
        }
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(TextHelper.formatted(CoreConfig.QUIT_MESSAGE
                .replace("{PLAYER_NAME}", event.getPlayer().getName())
        ));
    }

    @EventHandler
    private void onPing(ServerListPingEvent event) {
        if (CoreConfig.MESSAGES_OF_THE_DAY.isEmpty()) {
            return;
        }

        int idx = MathHelper.randomInt(0, CoreConfig.MESSAGES_OF_THE_DAY.size());
        String motd = CoreConfig.MESSAGES_OF_THE_DAY.get(idx);

        event.setMotd(TextHelper.formatted(motd));
    }
}
