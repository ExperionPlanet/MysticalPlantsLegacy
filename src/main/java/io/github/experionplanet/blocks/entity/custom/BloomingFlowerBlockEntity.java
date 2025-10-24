package io.github.experionplanet.blocks.entity.custom;


import io.github.experionplanet.init.MPLBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class BloomingFlowerBlockEntity extends BlockEntity {
    public String CONTENT_TYPE = "null";
    public BloomingFlowerBlockEntity(BlockPos pos, BlockState state) {
        super(MPLBlockEntities.BLOOMING_FLOWER, pos, state);
    }

}
