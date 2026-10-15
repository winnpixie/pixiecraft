package io.github.winnpixie.pixiecraft.core.plugin.handlers;

import io.github.winnpixie.pixiecraft.commons.BaseEventHandler;
import io.github.winnpixie.pixiecraft.commons.PDCWrapper;
import io.github.winnpixie.pixiecraft.core.plugin.PxCorePlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

/**
 * Legacy handler for a previous iteration of the Book of Knowledge.
 */
public class LegacyBoKHandler extends BaseEventHandler<PxCorePlugin> {
    public LegacyBoKHandler(PxCorePlugin plugin) {
        super(plugin);
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR
                && action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        ItemStack item = event.getItem();
        if (item == null
                || item.getType() != Material.BOOK) {
            return;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }

        PDCWrapper<PxCorePlugin> pdc = new PDCWrapper<>(getPlugin(), meta);
        if (!pdc.has("bok_levels", PersistentDataType.INTEGER)) {
            return;
        }

        int levels = pdc.getInt("bok_levels");
        Player player = event.getPlayer();
        player.setLevel(player.getLevel() + levels);
        item.setAmount(item.getAmount() - 1);

        player.spigot().sendMessage(new ComponentBuilder("You read a ")
                .color(ChatColor.DARK_PURPLE)
                .append("Book of Knowledge")
                .color(ChatColor.LIGHT_PURPLE)
                .append(" and learned ")
                .color(ChatColor.DARK_PURPLE)
                .append("%d level(s)".formatted(levels))
                .color(ChatColor.LIGHT_PURPLE)
                .append(" of experience")
                .color(ChatColor.DARK_PURPLE)
                .build());
    }
}
