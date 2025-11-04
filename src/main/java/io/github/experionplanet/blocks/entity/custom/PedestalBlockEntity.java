package io.github.experionplanet.blocks.entity.custom;

import io.github.experionplanet.blocks.entity.ContainerBlockEntity;
import io.github.experionplanet.init.MPLBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class PedestalBlockEntity extends ContainerBlockEntity {
    public PedestalBlockEntity(BlockPos pos, BlockState state) {
        super(MPLBlockEntities.PEDESTAL,pos, state,1);
    }
}
