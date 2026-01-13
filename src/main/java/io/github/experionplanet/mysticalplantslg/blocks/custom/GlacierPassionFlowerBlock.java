package io.github.experionplanet.mysticalplantslg.blocks.custom;

import io.github.experionplanet.mysticalplantslg.blocks.BloomingFlowerBlock;
import io.github.experionplanet.mysticalplantslg.blocks.entity.custom.BloomingFlowerBlockEntity;
import io.github.experionplanet.mysticalplantslg.init.MPLItems;
import io.github.experionplanet.mysticalplantslg.init.MPLSoundEvents;
import io.github.experionplanet.mysticalplantslg.init.MPLStatusEffects;
import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import io.github.experionplanet.mysticalplantslg.utils.SnowableBlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static io.github.experionplanet.mysticalplantslg.init.MPLBlockProperties.SNOW;

public class GlacierPassionFlowerBlock extends BloomingFlowerBlock {
    public GlacierPassionFlowerBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(SNOW, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(SNOW);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return SnowableBlockUtils.getPlacementState(ctx, super.getPlacementState(ctx));
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
        SnowableBlockUtils.neighborUpdate(state, world, pos);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SnowableBlockUtils.getVoxelShape(state, super.getOutlineShape(state, world, pos, context));
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BloomingFlowerBlockEntity(pos, state);
    }

    @Override
    protected void onHarvest(BlockState state, ServerWorld world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        Random rand = world.getRandom();
        Vec3d vec = MysticalUtils.v3dConvert(pos, true);
        ItemStack stack;
        if (rand.nextFloat() <= 0.1f) {
            stack = new ItemStack(MPLItems.FROST_ESSENCE);
            world.playSound(null, pos, MPLSoundEvents.FROST_ESSENCE_POPUP, SoundCategory.BLOCKS);
        }else {
            stack = new ItemStack(MPLItems.PERMAFROST_SNOWFLAKE);
        }
        ItemEntity itemEntity = new ItemEntity(world, vec.getX(), vec.getY(), vec.getZ(), stack);
        itemEntity.addVelocity(
                MysticalUtils.doubleInRange(rand,-0.1, 0.1),
                MysticalUtils.doubleInRange(rand,0, 0.2),
                MysticalUtils.doubleInRange(rand,-0.1, 0.1)
        );

        world.spawnEntity(itemEntity);
        world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS);

    }
}
