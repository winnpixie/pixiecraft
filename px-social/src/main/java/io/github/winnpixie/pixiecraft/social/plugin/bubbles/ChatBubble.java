package io.github.winnpixie.pixiecraft.social.plugin.bubbles;

import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;

import java.util.UUID;

public class ChatBubble {
    private static final Color BLANK = Color.fromARGB(0, 0, 0, 0);

    private final UUID playerId;
    private final TextDisplay display;

    private int ticksVisible;

    public ChatBubble(UUID playerId, TextDisplay display) {
        this.playerId = playerId;
        this.display = display;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public TextDisplay getDisplay() {
        return display;
    }

    public void display(String message) {
        display(message, 140); // 7 seconds, 7 seconds is all I can spare to show your text...
    }

    public void display(String message, int ticks) {
        display.setText(message);
        display.setBackgroundColor(Color.BLACK);

        ticksVisible = ticks;
    }

    public void tick(ChatBubbleTracker<?> tracker) {
        if (ticksVisible > 0) {
            ticksVisible--;
        } else if (ticksVisible == 0) {
            display.setText("");
            display.setBackgroundColor(BLANK);

            ticksVisible = -1;
        }

        Player player = tracker.getPlugin().getServer().getPlayer(playerId);
        if (player == null) {
            tracker.remove(this);
            return;
        }

        display.teleport(player.getLocation()
                .add(0.0, player.getHeight() + 0.69, 0.0));
    }
}
