package io.github.winnpixie.pixiecraft.anticheat.plugin.users;

public class ActiveUser {
    private String lastSentMessage = "";
    private long lastMessageTime;

    public String getLastSentMessage() {
        return lastSentMessage;
    }

    public void setLastSentMessage(String lastSentMessage) {
        this.lastSentMessage = lastSentMessage;
    }

    public long getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(long lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }
}
