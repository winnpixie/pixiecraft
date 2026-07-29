package io.github.winnpixie.pixiecraft.social.plugin.bubbles;

import io.github.winnpixie.pixiecraft.commons.builders.EntityBuilder;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ChatBubbleTracker<P extends JavaPlugin> {
    private final Map<UUID, ChatBubble> bubbles = new ConcurrentHashMap<>();

    private final P plugin;

    public ChatBubbleTracker(P plugin) {
        this.plugin = plugin;
    }

    public Map<UUID, ChatBubble> getBubbles() {
        return bubbles;
    }

    public P getPlugin() {
        return plugin;
    }

    public void load() {
        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            for (Map.Entry<UUID, ChatBubble> entry : bubbles.entrySet()) {
                entry.getValue().tick(ChatBubbleTracker.this);
            }
        }, 0L, 0L);
    }

    public void add(Player player) {
        UUID playerId = player.getUniqueId();
        if (bubbles.containsKey(playerId)) {
            return;
        }

        TextDisplay display = EntityBuilder.of(TextDisplay.class)
                .at(player.getLocation())
                .gravity(false)
                .spawn(entity -> {
                    entity.setPersistent(false);
                    entity.setBillboard(Display.Billboard.CENTER);
                    entity.setTeleportDuration(2);
                    entity.setInterpolationDuration(1);
                });

        bubbles.put(playerId, new ChatBubble(playerId, display));
    }

    public ChatBubble get(Player player) {
        return get(player.getUniqueId());
    }

    public ChatBubble get(UUID id) {
        return bubbles.get(id);
    }

    public void remove(Player player) {
        remove(player.getUniqueId());
    }

    public void remove(UUID id) {
        ChatBubble bubble = bubbles.get(id);
        if (bubble == null) {
            return;
        }

        remove(bubble);
    }

    public void remove(ChatBubble bubble) {
        bubbles.remove(bubble.getPlayerId());

        // Clean up entity from world, also.
        bubble.getDisplay().remove();
    }
}
