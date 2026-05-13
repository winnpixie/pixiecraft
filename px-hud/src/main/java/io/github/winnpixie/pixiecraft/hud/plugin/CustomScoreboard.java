package io.github.winnpixie.pixiecraft.hud.plugin;

import io.github.winnpixie.pixiecraft.commons.TextHelper;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class CustomScoreboard {
    private static final int MAX_ACCEPTABLE_LATENCY = 300;
    private static final String PREFIX = "\u00A7f> ";

    private final PxHUDPlugin plugin;

    public CustomScoreboard(PxHUDPlugin plugin) {
        this.plugin = plugin;
    }

    public void register(Player player) {
        Scoreboard scoreboard = plugin.getServer().getScoreboardManager().getNewScoreboard();

        Objective playerHealth = scoreboard.registerNewObjective("hud_health", Criteria.HEALTH, "Hearts", RenderType.HEARTS);
        playerHealth.setDisplaySlot(DisplaySlot.PLAYER_LIST);

        Objective sideBar = scoreboard.registerNewObjective("hud_sidebar", Criteria.DUMMY, "World Time", RenderType.INTEGER);
        sideBar.setDisplaySlot(DisplaySlot.SIDEBAR);
        sideBar.getScore("------------").setScore(9);

        // Ping
        Team ping = scoreboard.registerNewTeam("ping");
        ping.setColor(ChatColor.DARK_PURPLE);
        ping.setPrefix(PREFIX);
        ping.addEntry("Ping");
        sideBar.getScore("Ping").setScore(8);

        // SEPARATOR
        sideBar.getScore("\u00A7f>\u00A70").setScore(7);

        // Saturation
        Team saturation = scoreboard.registerNewTeam("saturation");
        saturation.setColor(ChatColor.YELLOW);
        saturation.setPrefix(PREFIX);
        saturation.addEntry("Sat.");
        sideBar.getScore("Sat.").setScore(6);

        // SEPARATOR
        sideBar.getScore(">\u00A71").setScore(5);

        // GPS
        Team direction = scoreboard.registerNewTeam("direction");
        direction.setColor(ChatColor.DARK_GRAY);
        direction.setPrefix(PREFIX);
        direction.addEntry("Dir.");
        sideBar.getScore("Dir.").setScore(4);

        Team coordX = scoreboard.registerNewTeam("x");
        coordX.setColor(ChatColor.GRAY);
        coordX.setPrefix(PREFIX);
        coordX.addEntry("X");
        sideBar.getScore("X").setScore(3);

        Team coordY = scoreboard.registerNewTeam("y");
        coordY.setColor(ChatColor.GRAY);
        coordY.setPrefix(PREFIX);
        coordY.addEntry("Y");
        sideBar.getScore("Y").setScore(2);

        Team coordZ = scoreboard.registerNewTeam("z");
        coordZ.setColor(ChatColor.GRAY);
        coordZ.setPrefix(PREFIX);
        coordZ.addEntry("Z");
        sideBar.getScore("Z").setScore(1);

        player.setScoreboard(scoreboard);
        update(player);
    }

    public void update(Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        Objective titleBar = scoreboard.getObjective("hud_sidebar");
        titleBar.setDisplayName(toTimeString(getTimeOfDay(player.getWorld())));

        Team ping = scoreboard.getTeam("ping");
        int latency = player.getPing();
        char latencyColor = TextHelper.getPercentColorCode(MAX_ACCEPTABLE_LATENCY - latency, MAX_ACCEPTABLE_LATENCY);
        ping.setSuffix(":\u00A7f \u00A7%c%dms".formatted(latencyColor, latency));

        Team saturation = scoreboard.getTeam("saturation");
        saturation.setSuffix("\u00A7f %.1f".formatted(player.getSaturation()));

        Team dir = scoreboard.getTeam("direction");
        dir.setSuffix("\u00A7f %s".formatted(player.getFacing().name()));

        Location location = player.getLocation();
        Team locX = scoreboard.getTeam("x");
        locX.setSuffix(":\u00A7f %.1f".formatted(location.getX()));

        Team locY = scoreboard.getTeam("y");
        locY.setSuffix(":\u00A7f %.1f".formatted(location.getY()));

        Team locZ = scoreboard.getTeam("z");
        locZ.setSuffix(":\u00A7f %.1f".formatted(location.getZ()));
    }

    private long getTimeOfDay(World world) {
        return (world.getFullTime() + 6000L) % 24000L;
    }

    private String toTimeString(long ticks) {
        double hour = (ticks / 24000.0) * 24.0;
        long hr = (long) Math.floor(hour);

        double minutes = (hour - hr) * 60.0;
        long min = (long) Math.floor(minutes);

        char meridiem = hr < 12 ? 'A' : 'P';

        hr = hr % 12;
        if (hr == 0) {
            hr = 12;
        }

        return "%s:%s %cM".formatted(hr < 10 ? "0" + hr : hr,
                min < 10 ? "0" + min : min, meridiem);
    }
}
