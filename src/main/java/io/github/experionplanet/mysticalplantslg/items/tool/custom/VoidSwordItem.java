package io.github.experionplanet.mysticalplantslg.items.tool.custom;

import io.github.experionplanet.mysticalplantslg.init.MPLSoundEvents;
import io.github.experionplanet.mysticalplantslg.init.MPLStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

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
        if (attacker.getWorld() instanceof ServerWorld world) {
            if (world.getRandom().nextFloat() <= 0.35f) {
                int amplifier = 0;

                if (target.hasStatusEffect(MPLStatusEffects.VOID)) {
                    amplifier = Math.clamp(target.getStatusEffect(MPLStatusEffects.VOID).getAmplifier() + 1, 0, 2);
                    if (amplifier > 2) {
                        return super.postHit(stack, target, attacker);
                    }
                }
                target.addStatusEffect(new StatusEffectInstance(MPLStatusEffects.VOID, 60, amplifier, false, true));
                world.playSound(null, target.getX(), target.getY(), target.getZ(), MPLSoundEvents.VOID_SWORD_EFFECT, SoundCategory.PLAYERS);
            }
        }



        return super.postHit(stack, target, attacker);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.empty());
        tooltip.add(Text.translatable("mysticalplantslg.tooltip.void_sword_1").formatted(Formatting.LIGHT_PURPLE).formatted(Formatting.BOLD));
        tooltip.add(Text.translatable("mysticalplantslg.tooltip.void_sword_2"));
        tooltip.add(Text.translatable("mysticalplantslg.tooltip.void_sword_3"));

        super.appendTooltip(stack, context, tooltip, type);
    }
}
