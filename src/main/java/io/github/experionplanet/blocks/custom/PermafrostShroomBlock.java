package io.github.experionplanet.blocks.custom;

import com.mojang.serialization.MapCodec;
import io.github.experionplanet.blocks.MysticalPlantBlockWithEntity;
import io.github.experionplanet.blocks.entity.LastTickedBlockEntity;
import io.github.experionplanet.blocks.entity.custom.PermafrostShroomBlockEntity;
import io.github.experionplanet.init.MPLStatusEffects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PermafrostShroomBlock extends MysticalPlantBlockWithEntity {
    public static final IntProperty CAP_REMAINING = IntProperty.of("cap_remaining", 0, 5);
    private static final int T_STEPPED = PermafrostShroomBlockEntity.T_STEPPED;
    private static final int COOLDOWN_STEP = 80;

    public PermafrostShroomBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(CAP_REMAINING, 5));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(CAP_REMAINING);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(PermafrostShroomBlock::new);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PermafrostShroomBlockEntity(pos, state);
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        int remaining = state.get(CAP_REMAINING);
        if (!world.isClient() && remaining > 0 && entity.isLiving()) {
            LivingEntity livingEntity = (LivingEntity) entity;
            if (world.getBlockEntity(pos) instanceof PermafrostShroomBlockEntity && (entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ())) {
                PermafrostShroomBlockEntity blockEntity = (PermafrostShroomBlockEntity) world.getBlockEntity(pos);

                long ticked = blockEntity.getTicked(T_STEPPED);

                if (ticked == LastTickedBlockEntity.NULL_CLOCK || blockEntity.getTickedAsSeconds(T_STEPPED) >= COOLDOWN_STEP) {
                    blockEntity.triggerTick(T_STEPPED);
                    world.setBlockState(pos, state.with(CAP_REMAINING, remaining - 1));
                    livingEntity.addStatusEffect(new StatusEffectInstance(MPLStatusEffects.PERMAFROST, 100, 1, true, false, true));
                }


            }
        }
    }
}
