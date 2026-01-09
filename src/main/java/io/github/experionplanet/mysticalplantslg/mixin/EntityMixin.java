package io.github.experionplanet.mysticalplantslg.mixin;

import io.github.experionplanet.mysticalplantslg.init.MPLStatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin{
    @Shadow
    public boolean inPowderSnow;

    @Inject(method = "setInPowderSnow", at = @At("HEAD"), cancellable = true)
    private void onTicked(boolean powder,CallbackInfo cit) {
        Entity entity = (Entity)(Object)this;
        if (entity instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) entity;
            if (living.hasStatusEffect(MPLStatusEffects.FROST_RESISTANCE)) {
                cit.cancel();
            }
        }
    }



}
