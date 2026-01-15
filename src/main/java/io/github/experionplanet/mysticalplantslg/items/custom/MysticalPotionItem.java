package io.github.experionplanet.mysticalplantslg.items.custom;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.List;

public class MysticalPotionItem extends Item {
    private static final int MAX_USE_TIME = 32;
    private final StatusEffectInstance statusInst;

    public MysticalPotionItem(Settings settings, StatusEffectInstance statusInst) {
        super(settings);
        this.statusInst = statusInst;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return MAX_USE_TIME;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof ServerPlayerEntity serverPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        }

        if (!world.isClient) {
            user.addStatusEffect(
                    new StatusEffectInstance(
                            statusInst.getEffectType(),
                            statusInst.getDuration(),
                            statusInst.getAmplifier(),
                            statusInst.isAmbient(),
                            statusInst.shouldShowParticles(),
                            statusInst.shouldShowParticles()
                    )
            );

            stack.decrementUnlessCreative(1, user);
        }

        return super.finishUsing(stack, world, user);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        PotionContentsComponent.buildTooltip(List.of(statusInst), tooltip::add, 1.0F, context.getUpdateTickRate());


    }
}
