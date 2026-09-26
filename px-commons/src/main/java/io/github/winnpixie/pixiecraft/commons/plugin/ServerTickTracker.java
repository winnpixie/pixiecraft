package io.github.winnpixie.pixiecraft.commons.plugin;

public class ServerTickTracker implements Runnable {
    private long currentTick;

    public long getCurrentTick() {
        return currentTick;
    }

    @Override
    public void run() {
        this.currentTick++;
    }
}
