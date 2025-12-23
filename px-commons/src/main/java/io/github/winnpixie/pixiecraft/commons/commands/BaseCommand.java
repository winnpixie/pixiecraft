package io.github.winnpixie.pixiecraft.commons.commands;

import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class BaseCommand<P extends JavaPlugin> implements TabExecutor {
    private final String name;
    private final P plugin;

    protected BaseCommand(String name, P plugin) {
        this.name = name;
        this.plugin = plugin;
    }

    public String getName() {
        return name;
    }

    public P getPlugin() {
        return plugin;
    }

    public boolean register() {
        PluginCommand command = plugin.getCommand(name);
        if (command == null) return false;

        command.setExecutor(this);
        command.setTabCompleter(this);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }
}
