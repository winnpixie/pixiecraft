package io.github.winnpixie.pixiecraft.hud.plugin;

import io.github.winnpixie.pixiecraft.commons.config.Linked;

public class HUDConfig {
    private HUDConfig() {
    }

    @Linked("server-ip")
    public static String SERVER_IP;
}
