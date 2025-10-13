package io.github.winnpixie.pixiecraft.social.plugin;

import io.github.winnpixie.pixiecraft.social.plugin.handlers.PlayerChatHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class PxSocialPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        new PlayerChatHandler(this).register();
    }
}
