package io.github.winnpixie.pixiecraft.agriculture.plugin.handlers;

import io.github.winnpixie.pixiecraft.agriculture.plugin.PxAgriculturePlugin;
import io.github.winnpixie.pixiecraft.commons.BaseEventHandler;
import io.github.winnpixie.pixiecraft.commons.BlockHelper;
import io.github.winnpixie.pixiecraft.commons.CommonWarnings;
import io.github.winnpixie.pixiecraft.commons.PDCWrapper;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerActionHandler extends BaseEventHandler<PxAgriculturePlugin> {
    public PlayerActionHandler(PxAgriculturePlugin plugin) {
        super(plugin);
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        handleGrowth(event);

        handleStripping(event);
    }

    private void handleGrowth(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }

        if (block.getType() != Material.CACTUS
                && block.getType() != Material.SUGAR_CANE) {
            return;
        }

        ItemStack tool = event.getItem();
        if (tool == null) {
            return;
        }

        if (tool.getType() != Material.BONE_MEAL) {
            return;
        }

        Block[] tower = BlockHelper.getTower(block);
        Block bottom = tower[0];
        Block top = tower[1];
        int height = (top.getY() - bottom.getY()) + 1;
        if (height > 2) {
            return;
        }

        Block above = top.getRelative(BlockFace.UP);
        if (above.getY() >= above.getWorld().getMaxHeight()) {
            return;
        }

        if (!above.getType().isAir()) {
            return;
        }

        above.setType(block.getType());
        tool.setAmount(tool.getAmount() - 1);
        event.getPlayer().getEquipment().setItem(event.getHand(), tool);
    }

    private void handleStripping(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }

        if (!Tag.LOGS.isTagged(block.getType())) {
            return;
        }

        ItemStack tool = event.getItem();
        if (tool == null) {
            return;
        }

        if (!Tag.ITEMS_AXES.isTagged(tool.getType())) {
            return;
        }

        Player player = event.getPlayer();
        PDCWrapper<PxAgriculturePlugin> pdc = new PDCWrapper<>(getPlugin(), player);
        if (!pdc.has("axe_stripping") || pdc.getBoolean("axe_stripping")) {
            return;
        }

        player.spigot().sendMessage(CommonWarnings.CANNOT_EXECUTE);
        event.setCancelled(true);
    }
}
