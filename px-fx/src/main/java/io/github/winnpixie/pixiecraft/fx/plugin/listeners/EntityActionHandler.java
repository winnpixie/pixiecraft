package io.github.winnpixie.pixiecraft.fx.plugin.listeners;

import io.github.winnpixie.pixiecraft.commons.BaseEventHandler;
import io.github.winnpixie.pixiecraft.fx.plugin.PxEffectsPlugin;
import io.github.winnpixie.pixiecraft.fx.plugin.utilities.FxConfig;
import io.github.winnpixie.pixiecraft.fx.plugin.utilities.ParticleHelper;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityActionHandler extends BaseEventHandler<PxEffectsPlugin> {
    public EntityActionHandler(PxEffectsPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    private void onEntityTakeDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof LivingEntity entity)) {
            return;
        }

        Location location = event.getDamageSource().getDamageType() == DamageType.FALL ? entity.getLocation() :
                entity.getEyeLocation();


        // TODO: Configurable count and offset
        entity.getWorld().spawnParticle(Particle.BLOCK, location, FxConfig.PARTICLE_COUNT,
                FxConfig.OFFSET_X, FxConfig.OFFSET_Y, FxConfig.OFFSET_Z,
                ParticleHelper.getParticle(entity));
    }
}
