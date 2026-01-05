package io.github.experionplanet.status_effects;

import io.github.experionplanet.utils.MysticalUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class PossessedStatusEffect extends StatusEffect {
    public PossessedStatusEffect() {
        super(StatusEffectCategory.HARMFUL, ColorHelper.Argb.getArgb(96, 245, 450));
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        World world = entity.getWorld();
        Random rand = world.getRandom();

        entity.addVelocity(MysticalUtils.doubleInRange(rand, -0.5, 0.5), 0, MysticalUtils.doubleInRange(rand, -0.5, 0.5));

        if (rand.nextBetween(1, 5) == 1) {
            entity.jump();
        }

        return true;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int i = 40 >> amplifier;
        if (i > 0) {
            return duration % i == 0;
        } else {
            return true;
        }
    }
}
