package io.github.winnpixie.pixiecraft.commons;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Consumer;

public class ItemHelper {
    private ItemHelper() {
    }

    // Shamelessly stolen and re-implemented from Paper API's javadocs
    public static boolean editMeta(ItemStack stack, Consumer<? super ItemMeta> consumer) {
        return editMeta(stack, ItemMeta.class, consumer);
    }

    // Shamelessly stolen and re-implemented from Paper API's javadocs
    public static <T extends ItemMeta> boolean editMeta(ItemStack stack, Class<T> dataCls, Consumer<? super T> consumer) {
        ItemMeta meta = stack.getItemMeta();
        if (!dataCls.isInstance(meta)) return false;

        consumer.accept(dataCls.cast(meta));
        stack.setItemMeta(meta);
        return true;
    }
}
