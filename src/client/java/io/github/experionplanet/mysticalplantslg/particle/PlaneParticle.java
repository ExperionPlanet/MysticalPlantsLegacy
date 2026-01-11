package io.github.experionplanet.mysticalplantslg.particle;

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

public class PlaneParticle extends SpriteBillboardParticle {
    private static final Quaternionf QUATERNION = new Quaternionf(0F, -0.7F, 0.7F, 0F);
    protected final SpriteProvider sprite;
    private final boolean isGlowing;

    protected PlaneParticle(ClientWorld clientWorld, double x, double y, double z, double velX, double velY, double velZ, SpriteProvider sprites, int minAge, int maxAge, float setScale, boolean glow) {
        super(clientWorld, x, y, z, velX, velY, velZ);
        Random rand = clientWorld.getRandom();
        this.sprite = sprites;
        this.maxAge = rand.nextBetween(minAge, maxAge);
        this.isGlowing = glow;
        this.scale = setScale;
        this.setSprite(this.sprite);
        this.velocityX = 0;
        this.velocityY = 0;
        this.velocityZ = 0;
        this.setSpriteForAge(sprite);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isAlive()) {
            this.setSpriteForAge(sprite);
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        if (this.isGlowing) {
            return ParticleTextureSheet.PARTICLE_SHEET_LIT;
        }
       return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    // CREDIT: https://github.com/Superkat32/Explosive-Enhancement/blob/master/src/main/java/net/superkat/explosiveenhancement/particles/normal/BlastWaveParticle.java
    @Override
    public void buildGeometry(VertexConsumer buffer, Camera camera, float ticks) {
        Vec3d vec3 = camera.getPos();
        float x = (float) (MathHelper.lerp(ticks, this.prevPosX, this.x) - vec3.getX());
        float y = (float) (MathHelper.lerp(ticks, this.prevPosY, this.y) - vec3.getY());
        float z = (float) (MathHelper.lerp(ticks, this.prevPosZ, this.z) - vec3.getZ());

        Vector3f[] vector3fs = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};

        Vector3f[] vector3fsBottom = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, -1.0F, 0.0F)};

        float f4 = this.getSize(ticks);

        for (int i = 0; i < 4; ++i) {

            Vector3f vector3f = vector3fs[i];
            vector3f.rotate(QUATERNION);
            vector3f.mul(f4);
            vector3f.add(x, y + 0.01f, z);

            Vector3f vector3fBottom = vector3fsBottom[i];
            vector3fBottom.rotate(QUATERNION);
            vector3fBottom.mul(f4);
            vector3fBottom.add(x, y, z);

        }

        float f7 = this.getMinU();
        float f8 = this.getMaxU();
        float f5 = this.getMinV();
        float f6 = this.getMaxV();
        int light = this.getBrightness(ticks);

        // Render the top faces
        buffer.vertex(vector3fs[0].x(), vector3fs[0].y(), vector3fs[0].z()).texture(f8, f6).color(this.red, this.green, this.blue, this.alpha).light(light);
        buffer.vertex(vector3fs[1].x(), vector3fs[1].y(), vector3fs[1].z()).texture(f8, f5).color(this.red, this.green, this.blue, this.alpha).light(light);
        buffer.vertex(vector3fs[2].x(), vector3fs[2].y(), vector3fs[2].z()).texture(f7, f5).color(this.red, this.green, this.blue, this.alpha).light(light);
        buffer.vertex(vector3fs[3].x(), vector3fs[3].y(), vector3fs[3].z()).texture(f7, f6).color(this.red, this.green, this.blue, this.alpha).light(light);

        // Render the underside faces
        buffer.vertex(vector3fs[3].x(), vector3fs[3].y(), vector3fs[3].z()).texture(f7, f6).color(this.red, this.green, this.blue, this.alpha).light(light);
        buffer.vertex(vector3fs[2].x(), vector3fs[2].y(), vector3fs[2].z()).texture(f7, f5).color(this.red, this.green, this.blue, this.alpha).light(light);
        buffer.vertex(vector3fs[1].x(), vector3fs[1].y(), vector3fs[1].z()).texture(f8, f5).color(this.red, this.green, this.blue, this.alpha).light(light);
        buffer.vertex(vector3fs[0].x(), vector3fs[0].y(), vector3fs[0].z()).texture(f8, f6).color(this.red, this.green, this.blue, this.alpha).light(light);

    }

    @Environment(EnvType.CLIENT)
    public record Factory<T extends ParticleEffect>(SpriteProvider sprites, int minAge, int maxAge, float setScale, boolean glow) implements ParticleFactory<T> {
        public Particle createParticle(T type, ClientWorld world, double x, double y, double z, double vx, double vy, double vz) {
            return new PlaneParticle(world, x, y, z, vx, vy, vz, sprites, minAge, maxAge, setScale, glow);
        }
    }
}
