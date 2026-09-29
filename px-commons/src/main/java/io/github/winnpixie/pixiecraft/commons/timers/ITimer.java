package io.github.winnpixie.pixiecraft.commons.timers;

public interface ITimer {
    long getElapsed();

    default boolean hasElapsed(long duration) {
        return getElapsed() >= duration;
    }

    void reset();
}
