package io.github.winnpixie.pixiecraft.commons;

public interface TickTracker {
    long getCurrentTick();

    double getTickRate();

    double getTickTime();

    default float getTargetTickRate() {
        return 20f;
    }

    default float getMaxAcceptableTickTime() {
        return 1000f / getTargetTickRate();
    }
}
