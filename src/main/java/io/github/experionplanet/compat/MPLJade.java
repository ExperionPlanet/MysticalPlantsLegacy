package io.github.experionplanet.compat;

import io.github.experionplanet.blocks.custom.DisguiseOrchidBlock;
import io.github.experionplanet.entities.SporeEntity;
import io.github.experionplanet.init.MPLBlocks;
import io.github.experionplanet.init.MPLEntities;
import io.github.experionplanet.utils.ExperionLogger;
import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import snownee.jade.api.*;

@WailaPlugin
public class MPLJade implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {

    }

    // Even Jade mod won't help HAHAHAHAHAHA-
    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.addRayTraceCallback(((hitResult, accessor, accessor1) -> {
            if (accessor instanceof BlockAccessor blockAccessor) {
                BlockState state = blockAccessor.getBlockState();

                if (state.isOf(MPLBlocks.DISGUISE_ORCHID) && !state.get(DisguiseOrchidBlock.REVEALED)) {
                    return registration.blockAccessor().from(blockAccessor).blockState(Blocks.BLUE_ORCHID.getDefaultState()).build();
                }
            }

            return accessor;
        }));

        registration.hideTarget(MPLEntities.SPORES);


    }
}
