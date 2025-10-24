package io.github.experionplanet.status_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.world.World;

public class PermafrostStatusEffect extends StatusEffect {
    public PermafrostStatusEffect() {
        super(StatusEffectCategory.HARMFUL, ColorHelper.Argb.getArgb(255, 120, 203, 253));
        addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, Identifier.ofVanilla("effect.speed"), -0.3F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, Identifier.ofVanilla("effect.mining_fatigue"), -0.15F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!entity.isOnFire()) {
            World world = entity.getWorld();

            entity.damage(entity.getDamageSources().magic(), 0.5f * Math.max(amplifier, 1));
            entity.setInPowderSnow(true);
        }

        return super.applyUpdateEffect(entity, amplifier);
    }



}
