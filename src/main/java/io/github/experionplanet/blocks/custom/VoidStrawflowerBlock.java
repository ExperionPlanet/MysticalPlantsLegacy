package io.github.experionplanet.blocks.custom;

import com.mojang.serialization.MapCodec;
import io.github.experionplanet.blocks.BouncingPlantBlock;
import io.github.experionplanet.blocks.MysticalPlantBlockWithEntity;
import io.github.experionplanet.blocks.entity.LastTickedBlockEntity;
import io.github.experionplanet.blocks.entity.custom.BouncingPlantBlockEntity;
import io.github.experionplanet.blocks.entity.custom.PermafrostLogBlockEntity;
import io.github.experionplanet.blocks.entity.custom.VoidStrawflowerBlockEntity;
import io.github.experionplanet.init.MPLBlockEntities;
import io.github.experionplanet.init.MPLStatusEffects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static io.github.experionplanet.blocks.entity.custom.BouncingPlantBlockEntity.T_STEPPED;

public class VoidStrawflowerBlock extends MysticalPlantBlockWithEntity {
    public static final BooleanProperty IS_TRAPPED = BooleanProperty.of("is_trapped");

    public VoidStrawflowerBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(IS_TRAPPED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(IS_TRAPPED);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return super.canPlantOnTop(floor, world, pos) || floor.isOf(Blocks.END_STONE);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(VoidStrawflowerBlock::new);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new VoidStrawflowerBlockEntity(pos, state);
    }

    protected void onStepped(BlockState state, World world, BlockPos pos, LivingEntity entity) {
        if (!world.isClient()) {
            if (!entity.hasStatusEffect(MPLStatusEffects.ROOTED)) {
                VoidStrawflowerBlockEntity blockEntity = (VoidStrawflowerBlockEntity) world.getBlockEntity(pos);
                blockEntity.trapEntity(entity, pos, state, (ServerWorld) world);
            }
        }
    }

    protected boolean allowStepped(BlockState state, ServerWorld world, Entity entity) {
        return !state.get(IS_TRAPPED);
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient() && entity.isLiving()) {
            if (allowStepped(state, (ServerWorld) world, entity)) {
                LivingEntity livingEntity = (LivingEntity) entity;
                if (world.getBlockEntity(pos) instanceof VoidStrawflowerBlockEntity blockEntity && (entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ())) {
                    long ticked = blockEntity.getTicked(T_STEPPED);
                    if (ticked == LastTickedBlockEntity.NULL_CLOCK || blockEntity.getTicked(T_STEPPED) >= 60) {
                        blockEntity.triggerTick(T_STEPPED);
                        onStepped(state,world,pos,livingEntity);
                    }
                }
            }

        }
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (!world.isClient()) {
            return state.get(IS_TRAPPED) ? validateTicker(type, MPLBlockEntities.VOID_STRAWFLOWER, VoidStrawflowerBlockEntity::onServerTick) : null;
        }
        return super.getTicker(world,state,type);
    }

}
