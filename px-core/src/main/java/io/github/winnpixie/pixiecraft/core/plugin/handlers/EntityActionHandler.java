package io.github.winnpixie.pixiecraft.core.plugin.handlers;

import io.github.winnpixie.pixiecraft.commons.BaseEventHandler;
import io.github.winnpixie.pixiecraft.commons.MathHelper;
import io.github.winnpixie.pixiecraft.core.plugin.PxCorePlugin;
import org.bukkit.entity.Creeper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class EntityActionHandler extends BaseEventHandler<PxCorePlugin> {

    public EntityActionHandler(PxCorePlugin plugin) {
        super(plugin);
    }

    @EventHandler
    private void onSpawn(CreatureSpawnEvent event) {
        if (!(event.getEntity() instanceof Creeper creeper)) {
            return;
        }

        creeper.setPowered(MathHelper.randomInt(0, 1000) == 0);
    }
}
