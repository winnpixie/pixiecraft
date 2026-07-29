package io.github.winnpixie.pixiecraft.social.plugin;

import io.github.winnpixie.pixiecraft.social.plugin.bubbles.ChatBubbleTracker;
import io.github.winnpixie.pixiecraft.social.plugin.commands.*;
import io.github.winnpixie.pixiecraft.social.plugin.handlers.PlayerChatHandler;
import io.github.winnpixie.pixiecraft.social.plugin.handlers.PlayerConnectionHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class PxSocialPlugin extends JavaPlugin {
    private final ChatBubbleTracker<PxSocialPlugin> bubbleTracker = new ChatBubbleTracker<>(this);

    public ChatBubbleTracker<PxSocialPlugin> getBubbleTracker() {
        return bubbleTracker;
    }

    @Override
    public void onEnable() {
        bubbleTracker.load();

        new PlayerConnectionHandler(this).register();
        new PlayerChatHandler(this).register();

        new ChannelCommand(this).register();
        new ChatFilterCommand(this).register();
        new ChatColorCommand(this).register();
        new NicknameCommand(this).register();
        new WhisperCommand(this).register();
    }
}
