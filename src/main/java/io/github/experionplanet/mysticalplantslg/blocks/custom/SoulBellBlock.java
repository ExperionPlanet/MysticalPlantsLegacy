package io.github.experionplanet.mysticalplantslg.blocks.custom;

import com.mojang.serialization.MapCodec;
import io.github.experionplanet.mysticalplantslg.blocks.MysticalPlantBlockWithEntity;
import io.github.experionplanet.mysticalplantslg.blocks.entity.custom.SoulBellBlockEntity;
import io.github.experionplanet.mysticalplantslg.init.MPLBlockEntities;
import io.github.experionplanet.mysticalplantslg.init.MPLBlockTags;
import io.github.experionplanet.mysticalplantslg.init.MPLItems;
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
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SoulBellBlock extends MysticalPlantBlockWithEntity {
    public static final BooleanProperty ON_GOING = BooleanProperty.of("on_going");
    public static final BooleanProperty ON_REWARD = BooleanProperty.of("on_reward");
    public static final IntProperty REWARD_COUNT = IntProperty.of("reward_count", 0, 5);
    public static final IntProperty ROUND = IntProperty.of("round", 1, 3);

    public SoulBellBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState()
                .with(ON_GOING, false)
                .with(ON_REWARD, false)
                .with(REWARD_COUNT, 0)
                .with(ROUND, 1)
        );
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SoulBellBlockEntity(pos, state);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return super.canPlantOnTop(floor, world, pos) || floor.isIn(MPLBlockTags.SOUL_PLANT_SOIL);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(ON_GOING, ROUND, ON_REWARD, REWARD_COUNT);
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
            if (!state.get(ON_GOING) && player.getStackInHand(Hand.MAIN_HAND).isOf(MPLItems.SOUL_POLLEN)) {
                if (world.getBlockEntity(pos) instanceof SoulBellBlockEntity blockEntity) {
                    world.setBlockState(pos, state.with(ON_GOING, true));
                    player.getStackInHand(Hand.MAIN_HAND).decrement(1);
                    return ActionResult.SUCCESS_NO_ITEM_USED;
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
