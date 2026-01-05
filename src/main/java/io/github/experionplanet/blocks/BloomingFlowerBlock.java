package io.github.experionplanet.blocks;

import com.mojang.serialization.MapCodec;
import io.github.experionplanet.init.MPLBlockProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BloomingFlowerBlock extends MysticalPlantBlockWithEntity {
    public static final BooleanProperty BLOOMING = MPLBlockProperties.BLOOMING;

    public BloomingFlowerBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(BLOOMING, false));
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(BLOOMING);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return !state.get(BLOOMING);
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int resChance = random.nextBetween(1, 10);

        if (resChance <= 1) {
            world.setBlockState(pos, state.with(BLOOMING, true));
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

        if (state.get(BLOOMING)) {
            if (!world.isClient()) {
                world.setBlockState(pos, state.with(BLOOMING, false));
                onHarvest(state,(ServerWorld) world,pos,player,hit);
            }

            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    protected void onHarvest(BlockState state, ServerWorld world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {}

}
