package io.github.winnpixie.pixiecraft.core.plugin.commands;

import io.github.winnpixie.pixiecraft.commons.WarningMessages;
import io.github.winnpixie.pixiecraft.commons.commands.PlayerCommand;
import io.github.winnpixie.pixiecraft.core.plugin.PxCorePlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class HatCommand extends PlayerCommand<PxCorePlugin> {
    private final BaseComponent noItemError = new ComponentBuilder("You are not holding an item.")
            .color(ChatColor.RED)
            .build();

    public HatCommand(PxCorePlugin plugin) {
        super("hat", plugin);
    }

    @Override
    public boolean execute(Player player, Command command, String label, String[] args) {
        EntityEquipment equipment = player.getEquipment();
        if (equipment == null) {
            player.spigot().sendMessage(WarningMessages.CANNOT_EXECUTE);
            return false;
        }

        ItemStack held = equipment.getItem(EquipmentSlot.HAND);
        if (held.getType().isAir()) {
            player.spigot().sendMessage(noItemError);
            return false;
        }

        ItemStack helmet = equipment.getItem(EquipmentSlot.HEAD);
        equipment.setItem(EquipmentSlot.HAND, helmet);

        equipment.setItem(EquipmentSlot.HEAD, held);

        player.spigot().sendMessage(new ComponentBuilder("You are now wearing ")
                .color(ChatColor.DARK_PURPLE)
                .append(new TranslatableComponent(held.getTranslationKey()))
                .color(ChatColor.LIGHT_PURPLE)
                .build());
        return true;
    }
}
