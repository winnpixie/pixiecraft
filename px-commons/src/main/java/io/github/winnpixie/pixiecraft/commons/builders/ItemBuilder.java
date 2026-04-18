package io.github.winnpixie.pixiecraft.commons.builders;

import io.github.winnpixie.pixiecraft.commons.ItemHelper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.Consumer;

public class ItemBuilder {
    private Material type;
    private int quantity = 1;
    private String displayName;
    private List<String> lore;
    private boolean unbreakable;
    private EnchantmentGlintVisibility enchantmentGlintVisibility = EnchantmentGlintVisibility.DEFAULT;

    public static ItemBuilder of(Material type) {
        return new ItemBuilder().type(type);
    }

    public ItemBuilder type(Material type) {
        this.type = type;
        return this;
    }

    public ItemBuilder quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public ItemBuilder name(String name) {
        this.displayName = name;
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder unbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    public ItemBuilder shine(EnchantmentGlintVisibility enchantmentGlintVisibility) {
        this.enchantmentGlintVisibility = enchantmentGlintVisibility;
        return this;
    }

    public ItemStack craft() {
        ItemStack stack = new ItemStack(type, quantity);

        ItemHelper.editMeta(stack, meta -> {
            meta.setDisplayName(displayName);
            meta.setLore(lore);
            meta.setUnbreakable(unbreakable);

            if (enchantmentGlintVisibility != EnchantmentGlintVisibility.DEFAULT)
                meta.setEnchantmentGlintOverride(enchantmentGlintVisibility.isVisible());
        });

        return stack;
    }

    public <T extends ItemMeta> ItemStack craft(Class<T> metaCls, Consumer<? super T> mutator) {
        ItemStack stack = craft();
        ItemHelper.editMeta(stack, metaCls, mutator);

        return stack;
    }

    public enum EnchantmentGlintVisibility {
        FORCE_SHOW(true),
        FORCE_HIDE(false),
        DEFAULT(null);

        final Boolean visible;

        EnchantmentGlintVisibility(Boolean visible) {
            this.visible = visible;
        }

        public Boolean isVisible() {
            return visible;
        }
    }
}
