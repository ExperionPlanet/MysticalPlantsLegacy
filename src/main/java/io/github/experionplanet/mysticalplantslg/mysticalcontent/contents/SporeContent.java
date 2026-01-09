package io.github.experionplanet.mysticalplantslg.mysticalcontent.contents;

import net.minecraft.particle.ParticleType;

public class SporeContent {
    public final ParticleType<?> particle;
    public final String idStatusEffect;

    public SporeContent(ParticleType<?> particle, String statusEffect) {
        this.particle = particle;
        this.idStatusEffect = statusEffect;
    }
}
