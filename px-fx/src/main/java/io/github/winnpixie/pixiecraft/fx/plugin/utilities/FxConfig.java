package io.github.winnpixie.pixiecraft.fx.plugin.utilities;

import io.github.winnpixie.pixiecraft.commons.config.Linked;

public class FxConfig {
    private FxConfig() {
    }

    @Linked("particle-count")
    public static int PARTICLE_COUNT;

    @Linked("x-offset")
    public static double OFFSET_X;

    @Linked("y-offset")
    public static double OFFSET_Y;

    @Linked("z-offset")
    public static double OFFSET_Z;
}
