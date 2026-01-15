package io.github.experionplanet.mysticalplantslg.status_effects;

import io.github.experionplanet.mysticalplantslg.init.MPLParticles;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class VoidStatusEffect extends StatusEffect {
    public VoidStatusEffect() {
        super(StatusEffectCategory.HARMFUL,  ColorHelper.Argb.getArgb(125, 23, 159));
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        World world = entity.getWorld();
        if (!world.isClient()) {
            Box box = entity.getBoundingBox();
            Vec3d v = entity.getBoundingBox().getCenter();

            ((ServerWorld) world).spawnParticles(MPLParticles.VOID_MIST, v.getX(), v.getY(), v.getZ(), 1, box.getLengthX()/2d, box.getLengthY()/2d, box.getLengthZ()/2d, 0);

        }
        return super.applyUpdateEffect(entity, amplifier);
    }
}
