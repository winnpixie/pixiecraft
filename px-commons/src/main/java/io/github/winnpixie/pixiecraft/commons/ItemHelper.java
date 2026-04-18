package io.github.winnpixie.pixiecraft.commons;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Consumer;

public class ItemHelper {
    private ItemHelper() {
    }

    // Shamelessly stolen and re-implemented from Paper API's documentation
    public static boolean editMeta(ItemStack stack, Consumer<? super ItemMeta> mutator) {
        return editMeta(stack, ItemMeta.class, mutator);
    }

    // Shamelessly stolen and re-implemented from Paper API's documentation
    public static <T extends ItemMeta> boolean editMeta(ItemStack stack, Class<T> dataCls, Consumer<? super T> mutator) {
        ItemMeta meta = stack.getItemMeta();
        if (!dataCls.isInstance(meta)) return false;

        mutator.accept(dataCls.cast(meta));
        stack.setItemMeta(meta);
        return true;
    }
}
