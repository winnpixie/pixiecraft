package io.github.winnpixie.pixiecraft.social.plugin.bubbles;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;

import java.util.UUID;

public class ChatBubble {
    private static final Color BLANK = Color.fromARGB(0x00000000);
    private static final Color BACKGROUND = Color.fromARGB(0x47691337);

    private final UUID id;
    private final TextDisplay display;

    public ChatBubble(UUID id, TextDisplay display) {
        this.id = id;
        this.display = display;
    }

    private int time;

    public void display(String text) {
        display(text, 140); // 7 seconds
    }

    public void display(String text, int time) {
        display.setText(text);
        display.setBackgroundColor(BACKGROUND);

        this.time = time;
    }

    public void tick(ChatBubbleTracker tracker) {
        if (time > 0) {
            time--;
        } else if (time == 0) {
            display.setText("");
            display.setBackgroundColor(BLANK);

            time = -69;
        }

        Player player = tracker.getPlugin().getServer().getPlayer(id);
        if (player == null) {
            tracker.remove(id);
            return;
        }

        Location location = player.getLocation();
        // TODO: Make horizontal look vector accessible via pxCommons?
        float yaw = (float) Math.toRadians(location.getYaw());
        float vx = (float) -Math.sin(yaw);
        float vz = (float) Math.cos(yaw);

        if (!display.teleport(location
                .add(vx * -0.25, player.getHeight() + 0.47, vz * -0.25))) {
            tracker.remove(id);
            tracker.add(player);
        }
    }

    public void kill() {
        display.remove();
    }
}
