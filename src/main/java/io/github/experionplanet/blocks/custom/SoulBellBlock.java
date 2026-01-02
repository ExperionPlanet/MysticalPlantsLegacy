package io.github.experionplanet.blocks.custom;

import com.mojang.serialization.MapCodec;
import io.github.experionplanet.blocks.MysticalPlantBlockWithEntity;
import io.github.experionplanet.blocks.entity.custom.SoulBellBlockEntity;
import io.github.experionplanet.blocks.entity.custom.VoidStrawflowerBlockEntity;
import io.github.experionplanet.init.MPLBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SoulBellBlock extends MysticalPlantBlockWithEntity {
    public static final BooleanProperty ON_GOING = BooleanProperty.of("on_going");
    public static final IntProperty ROUND = IntProperty.of("round", 1, 3);

    public SoulBellBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState()
                .with(ON_GOING, false)
                .with(ROUND, 1)
        );
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SoulBellBlockEntity(pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(ON_GOING, ROUND);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(SoulBellBlock::new);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient()) {
            if (!state.get(ON_GOING)) {
                if (world.getBlockEntity(pos) instanceof SoulBellBlockEntity blockEntity) {
                    world.setBlockState(pos, state.with(ON_GOING, true));
                    return ActionResult.SUCCESS;
                }
            }
        }

        return ActionResult.PASS;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (!world.isClient()) {
            return state.get(ON_GOING) ? validateTicker(type, MPLBlockEntities.SOUL_BELL, SoulBellBlockEntity::onServerTick) : null;
        }

        return super.getTicker(world, state, type);
    }
}
