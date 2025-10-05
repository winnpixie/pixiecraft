package io.github.winnpixie.pixiecraft.commons.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class PxPlayerCommand<P extends JavaPlugin> extends PxCommand<P> {
    public PxPlayerCommand(String name, P plugin) {
        super(name, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            return execute(player, command, label, args);
        }

        return true;
    }

    public abstract boolean execute(Player sender, Command command, String label, String[] args);
}
