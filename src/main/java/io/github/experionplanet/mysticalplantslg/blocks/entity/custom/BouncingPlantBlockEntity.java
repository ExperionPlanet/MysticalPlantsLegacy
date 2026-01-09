package io.github.experionplanet.mysticalplantslg.blocks.entity.custom;

import io.github.experionplanet.mysticalplantslg.blocks.entity.LastTickedBlockEntity;
import io.github.experionplanet.mysticalplantslg.init.MPLBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class BouncingPlantBlockEntity extends LastTickedBlockEntity {
    public static final int T_STEPPED = 0;
    public static final int T_BOUNCED = 1;

    public BouncingPlantBlockEntity(BlockPos pos, BlockState state) {
        super(MPLBlockEntities.BOUNCING_PLANT, pos, state);
        addTick();
        addTick();
    }

    public void onStepped() {
        triggerTick(T_STEPPED);
    }


}
