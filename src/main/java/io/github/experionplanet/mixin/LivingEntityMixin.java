package io.github.experionplanet.mixin;

import io.github.experionplanet.init.MPLStatusEffects;
import io.github.experionplanet.utils.ExperionLogger;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
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

    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    private void rootingTravel(Vec3d movement, CallbackInfo cit) {
        LivingEntity selfEntity = (LivingEntity) (Object) this;

        if (selfEntity.hasStatusEffect(MPLStatusEffects.ROOTED)) {
            selfEntity.setVelocity(Vec3d.ZERO);
            cit.cancel();
        }
    }

    @ModifyArg(
            method = "damage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;applyDamage(Lnet/minecraft/entity/damage/DamageSource;F)V"
            ),
            index = 1
    )
    private float voidDamage(float baseValue) {
        LivingEntity selfEntity = (LivingEntity) (Object) this;

        if (selfEntity.hasStatusEffect(MPLStatusEffects.VOID)) {

            float multiplier = (1 + (0.5f * (selfEntity.getStatusEffect(MPLStatusEffects.VOID).getAmplifier() + 1)));
            float total = baseValue * multiplier;

            ExperionLogger.Print("BaseValue: " + baseValue);
            ExperionLogger.Print("Total: " + total);
            ExperionLogger.Print("Mult: " + multiplier);


            return total;
        }

        return baseValue;
    }
}
