package io.github.experionplanet.init;

import io.github.experionplanet.utils.ExperionUtils;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class MPLParticles {
    public static final SimpleParticleType EXP_SPORE = FabricParticleTypes.simple();
    public static final SimpleParticleType EXP_ENCHANT_LETTER = FabricParticleTypes.simple();
    public static final SimpleParticleType EXP_PIECES = FabricParticleTypes.simple();
    public static final SimpleParticleType BOG_SPORE = FabricParticleTypes.simple();
    public static final SimpleParticleType VOID_SPORE = FabricParticleTypes.simple();

    public static void bootstrap() {
        Registry.register(Registries.PARTICLE_TYPE, ExperionUtils.newId("exp_spore"), EXP_SPORE);
        Registry.register(Registries.PARTICLE_TYPE, ExperionUtils.newId("exp_enchant_letter"), EXP_ENCHANT_LETTER);
        Registry.register(Registries.PARTICLE_TYPE, ExperionUtils.newId("exp_pieces"), EXP_PIECES);
        Registry.register(Registries.PARTICLE_TYPE, ExperionUtils.newId("bog_spore"), BOG_SPORE);
        Registry.register(Registries.PARTICLE_TYPE, ExperionUtils.newId("void_spore"), VOID_SPORE);
    }
}
