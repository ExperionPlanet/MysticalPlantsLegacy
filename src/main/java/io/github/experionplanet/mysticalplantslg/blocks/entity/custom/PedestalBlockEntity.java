package io.github.experionplanet.mysticalplantslg.blocks.entity.custom;

import io.github.experionplanet.mysticalplantslg.blocks.entity.ContainerBlockEntity;
import io.github.experionplanet.mysticalplantslg.init.MPLBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class PedestalBlockEntity extends ContainerBlockEntity {
    public PedestalBlockEntity(BlockPos pos, BlockState state) {
        super(MPLBlockEntities.PEDESTAL,pos, state,1);
    }
}
