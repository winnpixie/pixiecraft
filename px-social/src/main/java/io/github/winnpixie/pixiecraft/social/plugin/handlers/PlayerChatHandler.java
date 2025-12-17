package io.github.winnpixie.pixiecraft.social.plugin.handlers;

import io.github.winnpixie.pixiecraft.commons.BaseEventHandler;
import io.github.winnpixie.pixiecraft.commons.PDCWrapper;
import io.github.winnpixie.pixiecraft.social.plugin.PxSocialPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.regex.Pattern;

public class PlayerChatHandler extends BaseEventHandler<PxSocialPlugin> {
    private final Pattern uwuPattern = Pattern.compile("[uor]", Pattern.CASE_INSENSITIVE);
    private final Pattern leetPattern = Pattern.compile("[abelostz]", Pattern.CASE_INSENSITIVE);

    public PlayerChatHandler(PxSocialPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    private void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        PDCWrapper<PxSocialPlugin> pdc = new PDCWrapper<>(getPlugin(), player.getPersistentDataContainer());

        // TODO: Implement proper logic
        if (!pdc.has("placeholder")) {
            event.setMessage(toLeet(toUwU(message)));
        }
    }

    private String toUwU(String text) {
        return uwuPattern.matcher(text).replaceAll(match -> {
            String letter = match.group();
            return switch (letter) {
                case "o" -> "owo";
                case "O" -> "OwO";
                case "u" -> "uwu";
                case "U" -> "UwU";
                case "r" -> "w";
                case "R" -> "W";
                default -> letter;
            };
        });
    }

    private String toLeet(String text) {
        return leetPattern.matcher(text).replaceAll(match -> {
            String letter = match.group();
            return switch (letter) {
                case "a", "A" -> "4";
                case "b", "B" -> "6";
                case "e", "E" -> "3";
                case "l", "L" -> "1";
                case "o", "O" -> "0";
                case "s", "S" -> "5";
                case "t", "T" -> "7";
                case "z", "Z" -> "2";
                default -> letter;
            };
        });
    }
}
