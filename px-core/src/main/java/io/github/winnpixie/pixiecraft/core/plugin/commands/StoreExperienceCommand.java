package io.github.winnpixie.pixiecraft.core.plugin.commands;

import io.github.winnpixie.pixiecraft.commons.MathHelper;
import io.github.winnpixie.pixiecraft.commons.PDCWrapper;
import io.github.winnpixie.pixiecraft.commons.WarningMessages;
import io.github.winnpixie.pixiecraft.commons.builders.ItemBuilder;
import io.github.winnpixie.pixiecraft.commons.commands.PlayerCommand;
import io.github.winnpixie.pixiecraft.core.plugin.PxCorePlugin;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StoreExperienceCommand extends PlayerCommand<PxCorePlugin> {
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

        ItemStack book = ItemBuilder.of(Material.BOOK)
                .name("Book of Knowledge")
                .shine(ItemBuilder.EnchantmentGlintVisibility.FORCE_SHOW)
                .craft(ItemMeta.class, meta -> {
                    PDCWrapper<PxCorePlugin> pdc = new PDCWrapper<>(getPlugin(), meta);
                    // pdc.setInt("bok_levels", levels);
                });


        return true;
    }
}
