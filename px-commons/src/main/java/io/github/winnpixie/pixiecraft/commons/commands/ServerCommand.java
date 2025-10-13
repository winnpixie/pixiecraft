package io.github.winnpixie.pixiecraft.commons.commands;

import io.github.winnpixie.pixiecraft.commons.CommonWarnings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ServerCommand<P extends JavaPlugin> extends BaseCommand<P> {
    protected ServerCommand(String name, P plugin) {
        super(name, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender console) {
            return execute(console, command, label, args);
        }

        sender.spigot().sendMessage(CommonWarnings.CONSOLE_ONLY);
        return true;
    }

    public abstract boolean execute(ConsoleCommandSender console, Command command, String label, String[] args);
}
