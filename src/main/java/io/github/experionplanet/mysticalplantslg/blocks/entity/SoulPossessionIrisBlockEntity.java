package io.github.experionplanet.mysticalplantslg.blocks.entity;

import io.github.experionplanet.mysticalplantslg.init.MPLBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class SoulPossessionIrisBlockEntity extends ContainerBlockEntity {
    public SoulPossessionIrisBlockEntity(BlockPos pos, BlockState state) {
        super(MPLBlockEntities.SOUL_POSSESSION_IRIS, pos, state, 1);
    }
}
