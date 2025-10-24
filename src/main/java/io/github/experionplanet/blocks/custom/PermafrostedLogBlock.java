package io.github.experionplanet.blocks.custom;

import com.mojang.serialization.MapCodec;
import io.github.experionplanet.blocks.entity.ContainerBlockEntity;
import io.github.experionplanet.blocks.entity.custom.PermafrostLogBlockEntity;
import io.github.experionplanet.init.MPLBlockEntities;
import io.github.experionplanet.init.MPLBlocks;
import io.github.experionplanet.init.MPLItems;
import io.github.experionplanet.utils.ExperionLogger;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class PermafrostedLogBlock extends BlockWithEntity {
    public static final BooleanProperty DEFROSTING = BooleanProperty.of("defrosting");

    public PermafrostedLogBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(DEFROSTING, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(DEFROSTING);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(PermafrostedLogBlock::new);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PermafrostLogBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (!world.isClient()) {
            return state.get(DEFROSTING) ? validateTicker(type, MPLBlockEntities.PERMAFROST_LOG, PermafrostLogBlockEntity::onTick) : null;
        }
        return super.getTicker(world,state,type);
    }
}
