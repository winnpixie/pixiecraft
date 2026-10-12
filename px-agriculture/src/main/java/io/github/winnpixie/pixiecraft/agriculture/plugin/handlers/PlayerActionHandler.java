package io.github.winnpixie.pixiecraft.agriculture.plugin.handlers;

import io.github.winnpixie.pixiecraft.agriculture.plugin.KeyTags;
import io.github.winnpixie.pixiecraft.agriculture.plugin.PxAgriculturePlugin;
import io.github.winnpixie.pixiecraft.commons.BaseEventHandler;
import io.github.winnpixie.pixiecraft.commons.BlockHelper;
import io.github.winnpixie.pixiecraft.commons.PDCWrapper;
import io.github.winnpixie.pixiecraft.commons.WarningMessages;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PlayerActionHandler extends BaseEventHandler<PxAgriculturePlugin> {
    public PlayerActionHandler(PxAgriculturePlugin plugin) {
        super(plugin);
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        handleGrowth(event);

        if (handleStripping(event)) {
            return;
        }

        if (handlePathing(event)) {
            return;
        }
    }

    private void handleGrowth(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        EquipmentSlot hand = event.getHand();
        if (hand == null) {
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
        event.getPlayer().getEquipment().setItem(hand, tool);
    }

    private boolean handleStripping(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return false;
        }

        Block block = event.getClickedBlock();
        if (block == null) {
            return false;
        }

        if (!Tag.LOGS.isTagged(block.getType())) {
            return false;
        }

        ItemStack tool = event.getItem();
        if (tool == null) {
            return false;
        }

        if (!Tag.ITEMS_AXES.isTagged(tool.getType())) {
            return false;
        }

        Player player = event.getPlayer();
        PDCWrapper<PxAgriculturePlugin> pdc = new PDCWrapper<>(getPlugin(), player);
        if (!pdc.has(KeyTags.LOG_STRIPPING) || pdc.getBoolean(KeyTags.LOG_STRIPPING)) {
            return false;
        }

        player.spigot().sendMessage(WarningMessages.CANNOT_EXECUTE);
        event.setCancelled(true);
        return true;
    }

    private boolean handlePathing(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return false;
        }

        Block block = event.getClickedBlock();
        if (block == null) {
            return false;
        }

        if (!Tag.TURNS_INTO_DIRT_PATH.isTagged(block.getType())) {
            return false;
        }

        ItemStack tool = event.getItem();
        if (tool == null) {
            return false;
        }

        if (!Tag.ITEMS_SHOVELS.isTagged(tool.getType())) {
            return false;
        }

        Player player = event.getPlayer();
        PDCWrapper<PxAgriculturePlugin> pdc = new PDCWrapper<>(getPlugin(), player);
        if (!pdc.has(KeyTags.DIRT_PATHING) || pdc.getBoolean(KeyTags.DIRT_PATHING)) {
            return false;
        }

        player.spigot().sendMessage(WarningMessages.CANNOT_EXECUTE);
        event.setCancelled(true);
        return true;
    }
}
