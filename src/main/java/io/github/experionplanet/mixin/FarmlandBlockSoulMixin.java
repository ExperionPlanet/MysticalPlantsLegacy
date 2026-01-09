package io.github.experionplanet.mixin;

import io.github.experionplanet.init.MPLBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static io.github.experionplanet.init.MPLBlockProperties.SOUL_NOT_ATTUNED;

@Mixin(FarmlandBlock.class)
public class FarmlandBlockSoulMixin {

    @Inject(method = "setToDirt", at = @At("HEAD"), cancellable = true)
    private static void setDirt(@Nullable Entity entity, BlockState state, World world, BlockPos pos, CallbackInfo cit) {
        if (state.getBlock() instanceof FarmlandBlock) {
            if (!state.get(SOUL_NOT_ATTUNED)) {
                cit.cancel();
            }
        }

    }

    @Inject(method = "appendProperties", at = @At("TAIL"))
    private void append(StateManager.Builder<Block, BlockState> builder, CallbackInfo cit) {
        builder.add(SOUL_NOT_ATTUNED);
    }


}
