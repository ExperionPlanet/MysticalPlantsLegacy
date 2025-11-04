package io.github.experionplanet.blocks.custom;

import com.mojang.serialization.MapCodec;
import io.github.experionplanet.blocks.BouncingPlantBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static io.github.experionplanet.init.MPLBlockProperties.CAP_REMAINING;

public class VoidCapBlock extends BouncingPlantBlock {
    public VoidCapBlock(Settings settings) {
        super(settings, 60);
        this.setDefaultState(getDefaultState().with(CAP_REMAINING, 5));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(VoidCapBlock::new);
    }

    @Override
    protected boolean allowStepped(BlockState state, ServerWorld world, Entity entity) {
        return state.get(CAP_REMAINING) > 0;
    }

    @Override
    protected void onStepped(BlockState state, World world, BlockPos pos, LivingEntity entity) {
        int total = state.get(CAP_REMAINING) - 1;

        if (total == 0) {

        }else {

        }

        world.setBlockState(pos, state.with(CAP_REMAINING, total));

    }
}
