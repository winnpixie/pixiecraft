package io.github.winnpixie.pixiecraft.social.plugin.bubbles;

import io.github.winnpixie.pixiecraft.commons.builders.EntityBuilder;
import io.github.winnpixie.pixiecraft.social.plugin.PxSocialPlugin;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ChatBubbleTracker {
    private final PxSocialPlugin plugin;

    private final Map<UUID, ChatBubble> tracked = new ConcurrentHashMap<>();

    public ChatBubbleTracker(PxSocialPlugin plugin) {
        this.plugin = plugin;
    }

    public PxSocialPlugin getPlugin() {
        return plugin;
    }

    public void load() {
        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            for (Map.Entry<UUID, ChatBubble> entry : tracked.entrySet()) {
                entry.getValue().tick(this);
            }
        }, 0L, 0L);
    }

    public ChatBubble add(Player player) {
        UUID id = player.getUniqueId();
        TextDisplay display = EntityBuilder.of(TextDisplay.class)
                .at(player.getLocation())
                .spawn(entity -> {
                    entity.setPersistent(false);
                    entity.setBillboard(Display.Billboard.CENTER);
                    entity.setTeleportDuration(1);
                    entity.setInterpolationDuration(1);
                });

        ChatBubble bubble = new ChatBubble(id, display);
        tracked.put(id, bubble);

        return bubble;
    }

    public ChatBubble remove(Player player) {
        return remove(player.getUniqueId());
    }

    public ChatBubble remove(UUID id) {
        ChatBubble bubble = tracked.remove(id);
        if (bubble != null) {
            bubble.kill();
        }

        return bubble;
    }

    public ChatBubble get(Player player) {
        return get(player.getUniqueId());
    }

    public ChatBubble get(UUID id) {
        return tracked.get(id);
    }
}
