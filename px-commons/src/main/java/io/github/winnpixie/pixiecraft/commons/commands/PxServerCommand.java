package io.github.winnpixie.pixiecraft.commons.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class PxServerCommand<P extends JavaPlugin> extends PxCommand<P> {
    public PxServerCommand(String name, P plugin) {
        super(name, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender console) {
            return execute(console, command, label, args);
        }

        return true;
    }

    public abstract boolean execute(ConsoleCommandSender console, Command command, String label, String[] args);
}
