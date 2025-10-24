package io.github.experionplanet.blocks.entity.custom;

import io.github.experionplanet.blocks.entity.LastTickedBlockEntity;
import io.github.experionplanet.init.MPLBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class BouncingPlantBlockEntity extends LastTickedBlockEntity {
    public static final int T_STEPPED = 0;

    public BouncingPlantBlockEntity(BlockPos pos, BlockState state) {
        super(MPLBlockEntities.BOUNCING_PLANT, pos, state);
        addTick();
    }

    public void onStepped() {
        triggerTick(T_STEPPED);
    }


}
