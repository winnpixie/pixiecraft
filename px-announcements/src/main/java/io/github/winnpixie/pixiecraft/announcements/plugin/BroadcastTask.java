package io.github.winnpixie.pixiecraft.announcements.plugin;

import io.github.winnpixie.pixiecraft.commons.MathHelper;
import io.github.winnpixie.pixiecraft.commons.TextHelper;

import java.util.List;

public class BroadcastTask implements Runnable {
    private final PxAnnouncementsPlugin plugin;

    private int lastIndex = -1;

    public BroadcastTask(PxAnnouncementsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        List<String> messages = AnnouncerConfig.MESSAGES;
        if (messages.isEmpty()) {
            return;
        }

        int idx = lastIndex + 1;
        if (AnnouncerConfig.RANDOMIZE) {
            do {
                idx = MathHelper.randomInt(0, messages.size());
            } while (idx == lastIndex);
        } else if (idx == messages.size()) {
            idx = 0;
        }
        lastIndex = idx;

        String message = messages.get(idx);
        plugin.getServer().broadcastMessage(TextHelper.formatted("%s%s%s".formatted(
                AnnouncerConfig.PREFIX, message, AnnouncerConfig.SUFFIX
        )));
    }
}
