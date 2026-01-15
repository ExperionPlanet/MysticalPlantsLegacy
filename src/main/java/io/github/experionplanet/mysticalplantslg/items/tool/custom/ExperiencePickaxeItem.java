package io.github.experionplanet.mysticalplantslg.items.tool.custom;

import io.github.experionplanet.mysticalplantslg.init.MPLComponentTypes;
import io.github.experionplanet.mysticalplantslg.init.MPLBlockTags;
import io.github.experionplanet.mysticalplantslg.init.MPLParticles;
import io.github.experionplanet.mysticalplantslg.init.MPLSoundEvents;
import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.block.BlockState;
import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ExperiencePickaxeItem extends PickaxeItem {
    public static final int MAX_FILLS = 64;
    public static final ComponentType<Integer> EXP_FILLS = MPLComponentTypes.EXP_FILLS;

    public ExperiencePickaxeItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    public static int getEXP_FILLS(ItemStack stack) {
        if (stack.contains(EXP_FILLS)) {
            return stack.get(EXP_FILLS);
        }
        return 0;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.empty());
        tooltip.add(Text.translatable("mysticalplantslg.tooltip.experience_pickaxe_1").formatted(Formatting.GREEN).formatted(Formatting.BOLD));
        tooltip.add(Text.translatable("mysticalplantslg.tooltip.experience_pickaxe_2"));

        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        boolean succ = super.postMine(stack, world, state, pos, miner);

        if (succ) {
            if (EnchantmentHelper.getLevel(world.getRegistryManager().getWrapperOrThrow(Enchantments.SILK_TOUCH.getRegistryRef()).getOrThrow(Enchantments.SILK_TOUCH), stack) == 0 && getEXP_FILLS(stack) < MAX_FILLS) {
                if (state.isIn(MPLBlockTags.ORES)) {
                    stack.set(EXP_FILLS, getEXP_FILLS(stack) + 1);
                }
            }
        }

        return succ;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient()) {
            if (getEXP_FILLS(stack) >= MAX_FILLS) {
                stack.set(EXP_FILLS, 0);
                ExperienceOrbEntity.spawn((ServerWorld) world, user.getPos().add(0, 0.5d, 0), world.getRandom().nextBetween(74, 122));
                world.playSound(null, user.getX(), user.getY(), user.getZ(), MPLSoundEvents.EXPERIENCE_PICKAXE_BURST, SoundCategory.PLAYERS);
                ServerWorld serverWorld = (ServerWorld) world;
                serverWorld.spawnParticles(MPLParticles.EXP_SPORE, user.getX(), user.getY() + 0.5d, user.getZ(), world.getRandom().nextBetween(3, 5), 0,0.2,0, 0);
                serverWorld.spawnParticles(MPLParticles.EXP_DRIP_YELLOW, user.getX(), user.getY() + 0.5d, user.getZ(), world.getRandom().nextBetween(3, 8), 1,0.2,1, 0);
                serverWorld.spawnParticles(MPLParticles.EXP_DRIP_GREEN, user.getX(), user.getY() + 0.5d, user.getZ(), world.getRandom().nextBetween(3, 8), 1,0.2,1, 0);


                return TypedActionResult.success(stack);
            }
        }

        return TypedActionResult.pass(stack);
    }

    @Override
    public boolean canBeEnchantedWith(ItemStack stack, RegistryEntry<Enchantment> enchantment, EnchantingContext context) {
        if (enchantment.matchesKey(Enchantments.SILK_TOUCH)) {
            return false;
        }

        return super.canBeEnchantedWith(stack, enchantment, context);
    }
}
