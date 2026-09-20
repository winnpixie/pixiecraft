package io.github.winnpixie.pixiecraft.core.plugin.commands;

import io.github.winnpixie.pixiecraft.commons.TextHelper;
import io.github.winnpixie.pixiecraft.commons.commands.BaseCommand;
import io.github.winnpixie.pixiecraft.core.plugin.CoreConfig;
import io.github.winnpixie.pixiecraft.core.plugin.PxCorePlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jspecify.annotations.NonNull;

public class RulesCommand extends BaseCommand<PxCorePlugin> {
    private final BaseComponent messageHeader = new ComponentBuilder("Server Rules:")
            .color(ChatColor.DARK_PURPLE)
            .build();

    public RulesCommand(PxCorePlugin plugin) {
        super("rules", plugin);
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args) {
        sender.spigot().sendMessage(messageHeader);

        for (int i = 0; i < CoreConfig.SERVER_RULES.size(); i++) {
            sender.spigot().sendMessage(TextComponent.fromLegacy(TextHelper.formatted(
                    "<magenta>%d.<reset> %s"
                            .formatted(i + 1, CoreConfig.SERVER_RULES.get(i)))));
        }

        return true;
    }
}
