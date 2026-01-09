package io.github.experionplanet.mysticalplantslg.blocks.custom;

import com.mojang.serialization.MapCodec;
import io.github.experionplanet.mysticalplantslg.blocks.BouncingPlantBlock;
import io.github.experionplanet.mysticalplantslg.init.MPLSoundEvents;
import io.github.experionplanet.mysticalplantslg.init.MPLStatusEffects;
import io.github.experionplanet.mysticalplantslg.utils.SnowableBlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static io.github.experionplanet.mysticalplantslg.init.MPLBlockProperties.CAP_REMAINING;
import static io.github.experionplanet.mysticalplantslg.init.MPLBlockProperties.SNOW;

public class PermafrostShroomBlock extends BouncingPlantBlock {
    public PermafrostShroomBlock(Settings settings) {
        super(settings, 80);
        setDefaultState(getDefaultState().with(CAP_REMAINING, 5).with(SNOW, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(CAP_REMAINING,SNOW);
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
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(PermafrostShroomBlock::new);
    }

    @Override
    protected void onStepped(BlockState state, World world, BlockPos pos, LivingEntity entity) {
        int remaining = state.get(CAP_REMAINING);
        world.setBlockState(pos, state.with(CAP_REMAINING, remaining - 1));
        int totalRem = remaining - 1;
        int amplifier = 1;
        if (totalRem == 0) {
            amplifier = 2;
            world.playSound(null, pos, MPLSoundEvents.PERMAFROST_SHROOM_EXPLODE_LAST, SoundCategory.BLOCKS);
        }else {
            world.playSound(null, pos, MPLSoundEvents.PERMAFROST_SHROOM_EXPLODE, SoundCategory.BLOCKS);
        }
        entity.addStatusEffect(new StatusEffectInstance(MPLStatusEffects.PERMAFROST, 100, amplifier, true, false, true));

    }

    @Override
    protected boolean allowStepped(BlockState state, ServerWorld world, Entity entity) {
        return state.get(CAP_REMAINING) > 0;
    }


}
