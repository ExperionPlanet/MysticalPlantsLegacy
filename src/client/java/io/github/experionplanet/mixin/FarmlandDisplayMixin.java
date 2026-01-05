package io.github.experionplanet.mixin;

import io.github.experionplanet.init.MPLItems;
import io.github.experionplanet.utils.ExperionUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FarmlandBlock.class)
public class FarmlandDisplayMixin {
    @Inject(method = "randomDisplayTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/random/Random;)V", at = @At("TAIL"))
    private void display(BlockState state, World world, BlockPos pos, Random random, CallbackInfo ci) {
        PlayerEntity player = MinecraftClient.getInstance().player;

        if (player != null) {
            if (player.getStackInHand(Hand.MAIN_HAND).isOf(MPLItems.SOUL_HOE) || player.getStackInHand(Hand.OFF_HAND).isOf(MPLItems.SOUL_HOE)) {
                Vec3d v = ExperionUtils.v3dConvert(pos, true);
                world.addParticle(ParticleTypes.SOUL, v.getX(), v.getY() + 0.6d, v.getZ(), 0, 0, 0);
            }
        }
    }
}
