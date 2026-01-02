package io.github.experionplanet.items.tool.custom;

import io.github.experionplanet.init.MPLStatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.World;

public class VoidSwordItem extends SwordItem {
    public VoidSwordItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    /* <-- TOO SPECIFIC FOR VOID EFFECT, MOVED TO LIVINGENTITY.CLASS's MIXIN
    @Override
    public float getBonusAttackDamage(Entity target, float baseAttackDamage, DamageSource damageSource) {
        float dmg = super.getBonusAttackDamage(target, baseAttackDamage, damageSource);
        if (target instanceof LivingEntity living) {
            if (living.hasStatusEffect(MPLStatusEffects.VOID)) {
                int level = living.getStatusEffect(MPLStatusEffects.VOID).getAmplifier();
                float calculatedDMG = baseAttackDamage * (1f + (0.5f * (level + 1f)));
                return calculatedDMG - baseAttackDamage;
            }
        }
        return dmg;
    }*/

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        int amplifier = 0;

        if (target.hasStatusEffect(MPLStatusEffects.VOID)) {
            amplifier = Math.clamp(target.getStatusEffect(MPLStatusEffects.VOID).getAmplifier() + 1, 0, 2);
            if (amplifier > 2) {
                return super.postHit(stack, target, attacker);
            }
        }
        target.addStatusEffect(new StatusEffectInstance(MPLStatusEffects.VOID, 100, amplifier, false, false));


        return super.postHit(stack, target, attacker);
    }
}
