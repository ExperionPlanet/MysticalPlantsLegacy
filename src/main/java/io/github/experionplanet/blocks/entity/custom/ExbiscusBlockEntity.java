package io.github.experionplanet.blocks.entity.custom;


import io.github.experionplanet.init.MPLBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class ExbiscusBlockEntity extends BlockEntity {
    public ExbiscusBlockEntity(BlockPos pos, BlockState state) {
        super(MPLBlockEntities.EXBISCUS, pos, state);
    }

}
