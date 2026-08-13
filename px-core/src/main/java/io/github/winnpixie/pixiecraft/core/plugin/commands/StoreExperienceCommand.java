package io.github.winnpixie.pixiecraft.core.plugin.commands;

import io.github.winnpixie.pixiecraft.commons.MathHelper;
import io.github.winnpixie.pixiecraft.commons.PDCWrapper;
import io.github.winnpixie.pixiecraft.commons.WarningMessages;
import io.github.winnpixie.pixiecraft.commons.builders.ItemBuilder;
import io.github.winnpixie.pixiecraft.commons.commands.PlayerCommand;
import io.github.winnpixie.pixiecraft.core.plugin.PxCorePlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class StoreExperienceCommand extends PlayerCommand<PxCorePlugin> {
    private final BaseComponent noBookMessage = new ComponentBuilder("You must be holding a plain book.")
            .color(ChatColor.RED)
            .build();

    public StoreExperienceCommand(PxCorePlugin plugin) {
        super("store-experience", plugin);
    }

    @Override
    public boolean execute(Player player, Command command, String label, String[] args) {
        int levels = player.getLevel();
        if (args.length > 0) {
            if (!MathHelper.isInteger(args[0])) {
                player.spigot().sendMessage(WarningMessages.WRONG_ARGUMENT_TYPE);
                return false;
            }

            levels = Math.min(Integer.parseInt(args[0]), levels);
        }

        if (levels < 1) {
            player.spigot().sendMessage(WarningMessages.CANNOT_EXECUTE);
            return false;
        }

        ItemStack oldBook = getBook(player);
        if (oldBook == null) {
            player.spigot().sendMessage(noBookMessage);
            return false;
        }

        final int pdcValue = levels;
        ItemStack bookOfKnowledge = ItemBuilder.of(Material.BOOK)
                .name("Book of Knowledge")
                .shine(ItemBuilder.EnchantmentGlintVisibility.FORCE_SHOW)
                .lore(Arrays.asList(
                        "If one so desires to",
                        "read such arcane texts,",
                        "they shall inherit",
                        "%d level(s) of experience.".formatted(levels),
                        "\247f ", // can a blank line be... blank?
                        "With great power comes",
                        "great responsibility...")
                )
                .craft(ItemMeta.class, meta -> {
                    PDCWrapper<PxCorePlugin> pdc = new PDCWrapper<>(getPlugin(), meta);
                    pdc.setInt("bok_levels", pdcValue);
                });

        oldBook.setAmount(oldBook.getAmount() - 1);
        player.getInventory().addItem(bookOfKnowledge)
                .forEach((idx, item) -> player.getWorld().dropItem(player.getLocation(), item));

        player.setLevel(player.getLevel() - levels);

        player.spigot().sendMessage(new ComponentBuilder("You wrote ")
                .color(ChatColor.DARK_PURPLE)
                .append("%d level(s) ".formatted(levels))
                .color(ChatColor.LIGHT_PURPLE)
                .append("to a ")
                .color(ChatColor.DARK_PURPLE)
                .append("Book of Knowledge")
                .color(ChatColor.LIGHT_PURPLE)
                .build());
        return true;
    }

    private ItemStack getBook(Player player) {
        PlayerInventory inventory = player.getInventory();

        // Main Hand
        ItemStack item = inventory.getItem(EquipmentSlot.HAND);
        if (item != null
                && item.getType() == Material.BOOK) {
            return item;
        }

        // Off hand
        item = inventory.getItem(EquipmentSlot.OFF_HAND);
        if (item != null
                && item.getType() == Material.BOOK) {
            return item;
        }

        return null;
    }
}
