package io.github.experionplanet.mysticalplantslg.status_effects;

import io.github.experionplanet.mysticalplantslg.init.MPLParticles;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BasicStatusEffect extends StatusEffect {
    public BasicStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }
}
