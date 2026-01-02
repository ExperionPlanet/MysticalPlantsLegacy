package io.github.experionplanet.blocks;

import io.github.experionplanet.blocks.entity.LastTickedBlockEntity;
import io.github.experionplanet.blocks.entity.custom.BouncingPlantBlockEntity;
import io.github.experionplanet.init.MPLSoundEvents;
import io.github.experionplanet.init.MPLStatusEffects;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static io.github.experionplanet.blocks.entity.custom.BouncingPlantBlockEntity.T_STEPPED;

public class BouncingPlantBlock extends MysticalPlantBlockWithEntity {
    private final int COOLDOWN_STEP;
    private final boolean allowSteppedBounce;

    public BouncingPlantBlock(Settings settings, int cooldown, boolean allowSteppedBounce) {
        super(settings);
        this.COOLDOWN_STEP = cooldown;
        this.allowSteppedBounce = allowSteppedBounce;

    }

    public BouncingPlantBlock(Settings settings, int cooldown) {
        this(settings, cooldown, true);
    }


    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BouncingPlantBlockEntity(pos, state);
    }

    protected boolean allowStepped(BlockState state, ServerWorld world, Entity entity) {
        return true;
    }

    protected void onStepped(BlockState state, World world, BlockPos pos, LivingEntity entity) {

    }

    protected void bounceThePlant(World world, BlockPos pos) {
        BouncingPlantBlockEntity blockEntity = (BouncingPlantBlockEntity) world.getBlockEntity(pos);
        blockEntity.triggerTick(T_STEPPED);
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient() && entity.isLiving()) {
            if (allowStepped(state, (ServerWorld) world, entity)) {
                LivingEntity livingEntity = (LivingEntity) entity;
                if (world.getBlockEntity(pos) instanceof BouncingPlantBlockEntity && (entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ())) {
                    BouncingPlantBlockEntity blockEntity = (BouncingPlantBlockEntity) world.getBlockEntity(pos);
                    long ticked = blockEntity.getTicked(T_STEPPED);
                    if (ticked == LastTickedBlockEntity.NULL_CLOCK || blockEntity.getTickedAsSeconds(T_STEPPED) >= this.COOLDOWN_STEP) {
                        blockEntity.triggerTick(T_STEPPED);
                        onStepped(state,world,pos,livingEntity);
                        if (allowSteppedBounce) {
                            bounceThePlant(world, pos);
                        }
                    }
                }
            }

        }
    }
}
