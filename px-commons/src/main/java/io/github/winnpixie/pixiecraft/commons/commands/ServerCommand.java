package io.github.winnpixie.pixiecraft.commons.commands;

import io.github.winnpixie.pixiecraft.commons.WarningMessages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

public abstract class ServerCommand<P extends JavaPlugin> extends BaseCommand<P> {
    protected ServerCommand(String name, P plugin) {
        super(name, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender console) {
            return execute(console, command, label, args);
        }

        sender.spigot().sendMessage(WarningMessages.CONSOLE_ONLY);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender console) {
            return tabComplete(console, command, label, args);
        }

        return super.onTabComplete(sender, command, label, args);
    }

    public abstract boolean execute(ConsoleCommandSender console, Command command, String label, String[] args);

    public List<String> tabComplete(ConsoleCommandSender console, Command command, String label, String[] args) {
        return Collections.emptyList();
    }
}
