package io.github.winnpixie.pixiecraft.anticheat.plugin.users;

public class PassiveUser {
    private long lastQuitTime;

    public long getLastQuitTime() {
        return lastQuitTime;
    }

    public void setLastQuitTime(long lastQuitTime) {
        this.lastQuitTime = lastQuitTime;
    }
}
