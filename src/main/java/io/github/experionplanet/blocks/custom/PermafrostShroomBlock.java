package io.github.experionplanet.blocks.custom;

import com.mojang.serialization.MapCodec;
import io.github.experionplanet.blocks.BouncingPlantBlock;
import io.github.experionplanet.blocks.MysticalPlantBlockWithEntity;
import io.github.experionplanet.blocks.entity.LastTickedBlockEntity;
import io.github.experionplanet.blocks.entity.custom.BouncingPlantBlockEntity;
import io.github.experionplanet.init.MPLSoundEvents;
import io.github.experionplanet.init.MPLStatusEffects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static io.github.experionplanet.init.MPLBlockProperties.CAP_REMAINING;

public class PermafrostShroomBlock extends BouncingPlantBlock {
    public PermafrostShroomBlock(Settings settings) {
        super(settings, 80);
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
