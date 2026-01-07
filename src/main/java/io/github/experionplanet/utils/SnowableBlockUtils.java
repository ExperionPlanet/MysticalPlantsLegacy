package io.github.experionplanet.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static io.github.experionplanet.init.MPLBlockProperties.SNOW;

public class SnowableBlockUtils {
    private static final List<BlockPos> CHECK_POS = List.of(
            new BlockPos(1,0,0),
            new BlockPos(0,0,1),
            new BlockPos(-1,0,0),
            new BlockPos(0,0,-1)
    );

    private static final VoxelShape SNOW_SHAPE = Block.createCuboidShape((double)0.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)2.0F, (double)16.0F);

    public static boolean isItSurrounding(BlockPos center, World world) {
        boolean succ = true;

        for (BlockPos v : CHECK_POS) {
            if (!world.getBlockState(center.add(v)).isOf(Blocks.SNOW)) {
                succ = false;
                break;
            }
        }

        return succ;
    }

    public static @Nullable BlockState getPlacementState(ItemPlacementContext ctx, BlockState defaultState) {
        BlockPos pos = ctx.getBlockPos();
        World world = ctx.getWorld();
        if (isItSurrounding(pos, world)) {
            return defaultState.with(SNOW, true);
        }
        return defaultState.with(SNOW, false);
    }

    public static void neighborUpdate(BlockState state, World world, BlockPos pos) {
        if (isItSurrounding(pos, world)) {
            world.setBlockState(pos, state.with(SNOW, true));
        }else {
            world.setBlockState(pos, state.with(SNOW, false));
        }
    }

    public static VoxelShape getVoxelShape(BlockState state, VoxelShape defaultShape) {
        if (state.get(SNOW)) {
            return VoxelShapes.union(SNOW_SHAPE, defaultShape);
        }

        return defaultShape;
    }

}
