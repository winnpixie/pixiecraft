package io.github.winnpixie.pixiecraft.commons;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import java.util.function.Consumer;

public class BlockHelper {
    private BlockHelper() {
    }

    public static boolean editMetaWithoutPhysics(Block block, Consumer<? super BlockData> consumer) {
        return editMeta(block, BlockData.class, consumer, false);
    }

    public static <T extends BlockData> boolean editMetaWithoutPhysics(Block block, Class<T> dataCls, Consumer<? super T> consumer) {
        return editMeta(block, dataCls, consumer, false);
    }

    public static boolean editMeta(Block block, Consumer<? super BlockData> consumer) {
        return editMeta(block, BlockData.class, consumer, true);
    }

    public static <T extends BlockData> boolean editMeta(Block block, Class<T> dataCls, Consumer<? super T> consumer, boolean applyPhysics) {
        BlockData meta = block.getBlockData();
        if (!dataCls.isInstance(meta)) return false;

        consumer.accept(dataCls.cast(meta));
        block.setBlockData(meta, applyPhysics);
        return true;
    }
}
