package io.github.experionplanet.blocks.custom;

import io.github.experionplanet.blocks.BloomingFlowerBlock;
import io.github.experionplanet.blocks.entity.custom.BloomingFlowerBlockEntity;
import io.github.experionplanet.entities.SporeEntity;
import io.github.experionplanet.init.MPLItems;
import io.github.experionplanet.utils.MysticalUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
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
    protected void onHarvest(BlockState state, ServerWorld world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        Random rand =  world.getRandom();
        Vec3d vec = MysticalUtils.v3dConvert(pos, true);
        if (rand.nextFloat() <= 0.1f) {
            ItemStack stack = new ItemStack(MPLItems.BOGGED_ESSENCE);
            world.spawnEntity(new ItemEntity(world, vec.getX(), vec.getY(), vec.getZ(), stack));
        }else {
            SporeEntity spore = SporeEntity.createSpore(world, vec.getX(), vec.getY(), vec.getZ(), "bog");
            world.spawnEntity(spore);
            if (!player.hasStatusEffect(StatusEffects.HUNGER)) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 200, 1));
            }

        }
    }
}
