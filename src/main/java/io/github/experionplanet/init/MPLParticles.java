package io.github.experionplanet.init;

import io.github.experionplanet.utils.MysticalUtils;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class MPLParticles {
    public static final SimpleParticleType EXP_SPORE = FabricParticleTypes.simple();
    public static final SimpleParticleType BOG_SPORE = FabricParticleTypes.simple();
    public static final SimpleParticleType VOID_SPORE = FabricParticleTypes.simple();
    public static final SimpleParticleType ENDER_WARP = FabricParticleTypes.simple();
    public static final SimpleParticleType EXP_ASH = FabricParticleTypes.simple();

    public static void bootstrap() {
        Registry.register(Registries.PARTICLE_TYPE, MysticalUtils.newId("exp_spore"), EXP_SPORE);
        Registry.register(Registries.PARTICLE_TYPE, MysticalUtils.newId("bog_spore"), BOG_SPORE);
        Registry.register(Registries.PARTICLE_TYPE, MysticalUtils.newId("void_spore"), VOID_SPORE);
        Registry.register(Registries.PARTICLE_TYPE, MysticalUtils.newId("ender_warp"), ENDER_WARP);
        Registry.register(Registries.PARTICLE_TYPE, MysticalUtils.newId("exp_ash"), EXP_ASH);
    }
}
