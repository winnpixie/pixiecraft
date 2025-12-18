package io.github.winnpixie.pixiecraft.social.plugin;

import io.github.winnpixie.pixiecraft.social.plugin.commands.ChannelCommand;
import io.github.winnpixie.pixiecraft.social.plugin.commands.ChatFilterCommand;
import io.github.winnpixie.pixiecraft.social.plugin.commands.ColorCommand;
import io.github.winnpixie.pixiecraft.social.plugin.commands.NicknameCommand;
import io.github.winnpixie.pixiecraft.social.plugin.handlers.PlayerChatHandler;
import io.github.winnpixie.pixiecraft.social.plugin.handlers.PlayerConnectionHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class PxSocialPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        new PlayerConnectionHandler(this).register();
        new PlayerChatHandler(this).register();

        new ChannelCommand(this).register();
        new ChatFilterCommand(this).register();
        new ColorCommand(this).register();
        new NicknameCommand(this).register();
    }
}
