package io.github.experionplanet.blocks.custom;

import io.github.experionplanet.blocks.MysticalPlantBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DisguiseOrchidBlock extends MysticalPlantBlock {
    public static final BooleanProperty REVEALED = BooleanProperty.of("revealed");

    public DisguiseOrchidBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(REVEALED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(REVEALED);
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient() && entity.isLiving() && !state.get(REVEALED)) {
            LivingEntity livingEntity = (LivingEntity) entity;

            world.setBlockState(pos, state.with(REVEALED, true));

            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 60, 1, true, false, true));

        }
    }
}
