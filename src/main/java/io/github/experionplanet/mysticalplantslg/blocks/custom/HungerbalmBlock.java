package io.github.experionplanet.mysticalplantslg.blocks.custom;

import io.github.experionplanet.mysticalplantslg.blocks.BloomingFlowerBlock;
import io.github.experionplanet.mysticalplantslg.blocks.entity.custom.BloomingFlowerBlockEntity;
import io.github.experionplanet.mysticalplantslg.entities.SporeEntity;
import io.github.experionplanet.mysticalplantslg.init.MPLItems;
import io.github.experionplanet.mysticalplantslg.init.MPLSoundEvents;
import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class HungerbalmBlock extends BloomingFlowerBlock {
    public HungerbalmBlock(Settings settings) {
        super(settings);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BloomingFlowerBlockEntity(pos, state);
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return false;
    }

    @Override
    protected void onHarvest(BlockState state, ServerWorld world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!player.getStackInHand(Hand.MAIN_HAND).isOf(MPLItems.BOG_FERTILIZER)) {
            Random rand =  world.getRandom();
            Vec3d v = MysticalUtils.v3dConvert(pos, true);
            if (rand.nextFloat() <= 0.1f) {
                ItemStack stack = new ItemStack(MPLItems.BOGGED_ESSENCE);
                world.spawnEntity(new ItemEntity(world, v.getX(), v.getY(), v.getZ(), stack));
                world.playSound(null, pos, MPLSoundEvents.BOGGED_ESSENCE_POPUP, SoundCategory.BLOCKS);
            }else {
                SporeEntity spore = new SporeEntity(world, v.getX(), v.getY(), v.getZ(), MysticalUtils.newId("bog"));
                world.spawnEntity(spore);
                world.playSound(null, pos, MPLSoundEvents.SPORE, SoundCategory.BLOCKS);
                if (!player.hasStatusEffect(StatusEffects.HUNGER)) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 400, 2));
                }

            }
        }

    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        if (stack.isOf(MPLItems.BOG_FERTILIZER) && !state.get(BLOOMING)) {
            if (!world.isClient()) {
                Random rand = world.getRandom();
                stack.decrementUnlessCreative(1, player);
                world.playSound(null, pos, SoundEvents.BLOCK_MOSS_PLACE, SoundCategory.BLOCKS);
                Vec3d v = pos.toCenterPos();
                ((ServerWorld) world).spawnParticles(ParticleTypes.HAPPY_VILLAGER, v.getX(), v.getY(), v.getZ(), 8, 0.5, 0.5 ,0.5, 0);

                if (rand.nextFloat() <= 0.4f) {
                    world.setBlockState(pos, state.with(BLOOMING, true));
                }
            }
            return ItemActionResult.SUCCESS;
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }
}
