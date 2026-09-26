package io.github.winnpixie.pixiecraft.commons.timers;

public interface ITimer {
    boolean hasElapsed(long duration);

    void reset();
}
