package io.github.winnpixie.pixiecraft.fx.plugin.utilities;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;

public class ParticleHelper {
    private static BlockData tnt;
    private static BlockData lava;
    private static BlockData coalBlock;
    private static BlockData boneBlock;
    private static BlockData endPortal;
    private static BlockData redstoneBlock;

    private ParticleHelper() {
    }

    public static void init(Server server) {
        tnt = server.createBlockData(Material.TNT);
        lava = server.createBlockData(Material.LAVA);
        coalBlock = server.createBlockData(Material.COAL_BLOCK);
        boneBlock = server.createBlockData(Material.BONE_BLOCK);
        endPortal = server.createBlockData(Material.END_PORTAL);
        redstoneBlock = server.createBlockData(Material.REDSTONE_BLOCK);
    }

    public static BlockData getParticle(Entity entity) {
        return switch (entity.getType()) {
            case CREEPER -> tnt;
            case WITHER_SKELETON -> coalBlock;
            case SKELETON, SKELETON_HORSE -> boneBlock;
            case MAGMA_CUBE, STRIDER -> lava;
            case ENDERMAN, ENDERMITE, ENDER_DRAGON -> endPortal;
            default -> redstoneBlock;
        };
    }
}
