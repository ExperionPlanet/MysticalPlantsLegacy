package io.github.experionplanet.mysticalplantslg.blocks.custom;

import io.github.experionplanet.mysticalplantslg.blocks.MysticalPlantBlock;
import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class BleedingExpBlock extends MysticalPlantBlock {

    public BleedingExpBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int resChance =  random.nextBetween(1, 4);

        if (resChance <= 1) {
            ExperienceOrbEntity.spawn(world, MysticalUtils.v3dConvert(pos, true), random.nextBetween(1, 3));

            resChance = random.nextBetween(1, 20);

            if (resChance <= 1) {
                world.breakBlock(pos, false);
            }
        }
    }

}
