package io.github.winnpixie.pixiecraft.social.plugin;

import io.github.winnpixie.pixiecraft.commons.config.ConfigurationLoader;
import io.github.winnpixie.pixiecraft.economy.plugin.PxEconomyPlugin;
import io.github.winnpixie.pixiecraft.social.plugin.bubbles.ChatBubbleTracker;
import io.github.winnpixie.pixiecraft.social.plugin.commands.*;
import io.github.winnpixie.pixiecraft.social.plugin.handlers.PlayerChatHandler;
import io.github.winnpixie.pixiecraft.social.plugin.handlers.PlayerConnectionHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class PxSocialPlugin extends JavaPlugin {
    private final ChatBubbleTracker bubbles = new ChatBubbleTracker(this);

    private PxEconomyPlugin economy;
    private ConfigurationLoader configLoader;

    public ChatBubbleTracker getBubbles() {
        return bubbles;
    }

    public PxEconomyPlugin getEconomy() {
        return economy;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.configLoader = new ConfigurationLoader(getConfig());
        configLoader.link(SocialConfig.class);
        configLoader.load();

        economy = JavaPlugin.getPlugin(PxEconomyPlugin.class);
        bubbles.load();

        new PlayerConnectionHandler(this).register();
        new PlayerChatHandler(this).register();

        new ChannelCommand(this).register();
        new ChatFilterCommand(this).register();
        new ChatColorCommand(this).register();
        new NicknameCommand(this).register();
        new WhisperCommand(this).register();
    }
}
