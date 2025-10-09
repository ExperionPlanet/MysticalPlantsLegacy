package io.github.experionplanet.particle;

import io.github.experionplanet.utils.ExperionUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.random.Random;

public class BasicGlowingParticle extends SpriteBillboardParticle {
    private final SpriteProvider sprite;

    protected BasicGlowingParticle(ClientWorld clientWorld, double x, double y, double z, double vx, double vy, double vz, SpriteProvider sprite, float scale, int minAge, int maxAge) {
        super(clientWorld, x, y, z, vx, vy, vz);
        Random rand = clientWorld.getRandom();

        this.setVelocity(
                ExperionUtils.doubleInRange(rand,-.2, .2),
                0,
                ExperionUtils.doubleInRange(rand,-.2, .2)
        );
        this.ascending = true;
        this.velocityMultiplier = 0.75f;
        this.sprite = sprite;
        this.scale = scale;
        this.setSpriteForAge(sprite);
        this.maxAge = clientWorld.getRandom().nextBetween(minAge, maxAge);
    }

    @Override
    public void tick() {
        this.setSpriteForAge(sprite);
        super.tick();

    }
    /*
    @Override
    public void move(double dx, double dy, double dz) {
        this.setBoundingBox(this.getBoundingBox().offset(dx, dy, dz));
        this.repositionFromBoundingBox();
    }*/

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_LIT;
    }

    @Override
    protected int getBrightness(float tint) {
        return 0xE000E0;
    }

    @Environment(EnvType.CLIENT)
    public record Factory(SpriteProvider spriteProvider, float scale, int minAge, int maxAge) implements ParticleFactory<SimpleParticleType> {
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientWorld clientWorld, double x, double y, double z, double vx, double vy, double vz) {
            return new BasicGlowingParticle(clientWorld, x, y, z, vx, vy, vz, spriteProvider, scale, minAge, maxAge);
        }
    }
}
