package io.github.winnpixie.pixiecraft.commons.timers;

public class SysTimer implements ITimer {
    private long instant;

    public SysTimer() {
        reset();
    }

    public static long getNow() {
        return System.nanoTime() / 1000000;
    }

    @Override
    public boolean hasElapsed(long duration) {
        return getNow() - duration > instant;
    }

    @Override
    public void reset() {
        this.instant = getNow();
    }
}
