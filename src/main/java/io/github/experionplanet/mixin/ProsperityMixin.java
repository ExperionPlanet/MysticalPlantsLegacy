package io.github.experionplanet.mixin;

import io.github.experionplanet.init.MPLStatusEffects;
import io.github.experionplanet.utils.ExperionLogger;
import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class ProsperityMixin {
    @Inject(method = "getXpToDrop", at = @At("RETURN"), cancellable = true)
    private void prosExp(ServerWorld world, @Nullable Entity attacker, CallbackInfoReturnable<Integer> cit) {
        int res = cit.getReturnValue();

        if (attacker instanceof LivingEntity livingEntity) {
            if (livingEntity.hasStatusEffect(MPLStatusEffects.PROSPERITY)) {
                res = (int)(((double) res) * (1 + (0.5 * livingEntity.getStatusEffect(MPLStatusEffects.PROSPERITY).getAmplifier())));
                cit.setReturnValue(res);
            }
        }

        cit.setReturnValue(res);
    }
}
