package io.github.winnpixie.pixiecraft.core.plugin.commands;

import io.github.winnpixie.pixiecraft.commons.commands.PlayerCommand;
import io.github.winnpixie.pixiecraft.core.plugin.PxCorePlugin;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class ToggleSignEditingCommand extends PlayerCommand<PxCorePlugin> {
    public ToggleSignEditingCommand(PxCorePlugin plugin) {
        super("toggle-sign-editing", plugin);
    }

    @Override
    public boolean execute(Player player, Command command, String label, String[] args) {
        // TODO: Implement
        return true;
    }
}
