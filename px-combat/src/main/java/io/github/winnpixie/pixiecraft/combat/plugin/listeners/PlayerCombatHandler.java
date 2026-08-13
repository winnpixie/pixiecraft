package io.github.winnpixie.pixiecraft.combat.plugin.listeners;

import io.github.winnpixie.pixiecraft.combat.plugin.PxCombatPlugin;
import io.github.winnpixie.pixiecraft.commons.BaseEventHandler;
import io.github.winnpixie.pixiecraft.commons.MathHelper;
import io.github.winnpixie.pixiecraft.commons.builders.ItemBuilder;
import io.github.winnpixie.pixiecraft.economy.api.IUser;
import io.github.winnpixie.pixiecraft.economy.api.IWallet;
import io.github.winnpixie.pixiecraft.economy.plugin.UnitConverter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerCombatHandler extends BaseEventHandler<PxCombatPlugin> {
    public PlayerCombatHandler(PxCombatPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    private void onEntityDeath(EntityDeathEvent event) {
        LivingEntity victim = event.getEntity();
        if (!(victim.getLastDamageCause() instanceof EntityDamageByEntityEvent eveEvent)) {
            return;
        }

        if (!(eveEvent.getDamager() instanceof Player attacker)) {
            return;
        }

        if (victim instanceof Player poorSoul) {
            handleBeheading(attacker, poorSoul);
        } else {
            handleLooseChange(attacker);
        }
    }

    @EventHandler
    private void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        handleDeathMarker(player);

        handleWalletDrop(player);
    }

    private void handleDeathMarker(Player player) {
        Location deathLoc = player.getLocation();
        World world = deathLoc.getWorld();
        if (world == null) {
            return;
        }

        String coordinateFmt = "%.1f";
        player.spigot().sendMessage(new ComponentBuilder("You died at ")
                .color(ChatColor.DARK_PURPLE)
                .append("X ")
                .color(ChatColor.LIGHT_PURPLE)
                .append(coordinateFmt.formatted(deathLoc.getX()))
                .append(", Y ")
                .append(coordinateFmt.formatted(deathLoc.getY()))
                .append(", Z ")
                .append(coordinateFmt.formatted(deathLoc.getZ()))
                .append(" in the ")
                .color(ChatColor.DARK_PURPLE)
                .append(world.getEnvironment().name())
                .color(ChatColor.LIGHT_PURPLE)
                .append(" world")
                .color(ChatColor.DARK_PURPLE)
                .build());
    }

    private void handleWalletDrop(Player player) {
        IUser user = getPlugin().getEconomy().getUserManager().get(player);
        IWallet wallet = user.getWallet();
        if (wallet.getBalance() == 0L) {
            return;
        }

        long drop = MathHelper.randomLong(1L, 101L);
        drop = Math.min(drop, wallet.getBalance());
        wallet.spend(drop);

        player.spigot().sendMessage(new ComponentBuilder("Dropped ")
                .color(ChatColor.DARK_GREEN)
                .append(UnitConverter.toString(drop))
                .color(ChatColor.LIGHT_PURPLE)
                .append(" Fairy Dust")
                .color(ChatColor.DARK_PURPLE)
                .build());
    }

    private void handleBeheading(Player attacker, Player victim) {
        Material heldItem = attacker.getInventory().getItemInMainHand().getType();
        if (!Tag.ITEMS_SWORDS.isTagged(heldItem) && !Tag.ITEMS_AXES.isTagged(heldItem)) {
            return;
        }

        ItemStack head = ItemBuilder.of(Material.PLAYER_HEAD)
                .name("%s's head.".formatted(victim.getName()))
                .craft(SkullMeta.class, skull -> skull.setOwnerProfile(victim.getPlayerProfile()));

        attacker.getWorld().dropItemNaturally(victim.getLocation(), head);
    }

    private void handleLooseChange(Player attacker) {
        long drop = MathHelper.randomLong(1L, 101L);

        IUser user = getPlugin().getEconomy().getUserManager().get(attacker);
        user.getWallet().earn(drop);

        attacker.spigot().sendMessage(new ComponentBuilder("Picked up ")
                .color(ChatColor.DARK_GREEN)
                .append(UnitConverter.toString(drop))
                .color(ChatColor.LIGHT_PURPLE)
                .append(" Fairy Dust")
                .color(ChatColor.DARK_PURPLE)
                .build());
    }
}
