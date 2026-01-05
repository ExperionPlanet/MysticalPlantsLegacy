package io.github.experionplanet.particle;

import io.github.experionplanet.utils.ExperionUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class SporeParticle extends PlaneParticle {
    private static final double RangeVelo = 0.01;

    protected SporeParticle(ClientWorld clientWorld,  double x, double y, double z, double velX, double velY, double velZ, SpriteProvider sprites, float setScale, boolean glow) {
        super(clientWorld, x, y, z, velX, velY, velZ, sprites, 0, 1, setScale, glow);
        Random rand = clientWorld.getRandom();
        this.setVelocity(
                ExperionUtils.doubleInRange(rand,-0.1, 0.1),
                0,
                ExperionUtils.doubleInRange(rand,-.1, .1)
        );
        this.velocityMultiplier = 0.75f;
        this.ascending = true;
        this.maxAge = rand.nextBetween(20, 80);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isAlive()) {
            int currAge = this.age;
            float resAlpha = (float)currAge/(float)maxAge;
            this.alpha = 1f - resAlpha;
        }

    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    protected int getBrightness(float tint) {
        return 0xE000E0;
    }

    @Environment(EnvType.CLIENT)
    public record Factory<T extends ParticleEffect>(SpriteProvider sprites, float setScale, boolean glow) implements ParticleFactory<T> {
        public Particle createParticle(T type, ClientWorld world, double x, double y, double z, double vx, double vy, double vz) {
            return new SporeParticle(world, x, y ,z , vx, vy, vz, sprites, setScale, glow);
        }
    }

}
