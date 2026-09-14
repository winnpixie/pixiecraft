package io.github.winnpixie.pixiecraft.fx.plugin.utilities;

import io.github.winnpixie.pixiecraft.commons.config.Linked;

public class FxConfig {
    private FxConfig() {
    }

    @Linked("standard.particle-count")
    public static int PARTICLE_COUNT;

    @Linked("standard.x-offset")
    public static double OFFSET_X;

    @Linked("standard.y-offset")
    public static double OFFSET_Y;

    @Linked("standard.z-offset")
    public static double OFFSET_Z;

    @Linked("fart.chance")
    public static double FART_CHANCE;

    @Linked("fart.particle-count")
    public static int FART_PARTICLE_COUNT;

    @Linked("fart.x-offset")
    public static double FART_OFFSET_X;

    @Linked("fart.y-offset")
    public static double FART_OFFSET_Y;

    @Linked("fart.z-offset")
    public static double FART_OFFSET_Z;
}
