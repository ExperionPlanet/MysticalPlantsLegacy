package io.github.experionplanet.particle;

import io.github.experionplanet.utils.MysticalUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.random.Random;

import java.util.List;

import static io.github.experionplanet.particle.ParticleCreator.Props;

public class BasicMysticalParticle extends SpriteBillboardParticle {
    private final SpriteProvider sprite;
    private final ParticleCreator.Finalized creator;
    private final boolean animAngle;
    private final boolean animScale;
    private final float targetScale;
    private final float targetAngle;
    private final float baseScale;
    private final float baseAngle;

    protected BasicMysticalParticle(ClientWorld clientWorld, double x, double y, double z, double vx, double vy, double vz, SpriteProvider sprite, ParticleCreator.Finalized c) {
        super(clientWorld, x, y, z, vx, vy, vz);
        this.creator = c;
        Random rand = clientWorld.getRandom();

        if (creator.props.contains(Props.VELO_X)) {
            this.velocityX = creator.veloX.getDouble(rand);
        }

        if (creator.props.contains(Props.VELO_Y)) {
            this.velocityY = creator.veloY.getDouble(rand);
        }

        if (creator.props.contains(Props.VELO_Z)) {
            this.velocityZ = creator.veloZ.getDouble(rand);
        }

        if (creator.props.contains(Props.VELOCITY_MULTIPLIER)) {
            this.velocityMultiplier = creator.velocityMultiplier;
        }

        if (creator.props.contains(Props.GRAVITY_STRENGTH)) {
            this.gravityStrength = creator.gravityStrength;
        }

        this.ascending = creator.ascending;
        this.sprite = sprite;
        this.scale = creator.scale.getFloat(rand);
        this.angle = creator.angle;
        this.setSpriteForAge(sprite);
        this.maxAge = creator.age.getInt(rand);
        this.animAngle = creator.props.contains(Props.TARGET_ANGLE);
        this.animScale = creator.props.contains(Props.TARGET_SCALE);
        this.targetAngle = creator.targetAngle.getFloat(rand);
        this.targetScale = creator.targetScale.getFloat(rand);
        this.baseScale = this.scale;
        this.baseAngle = this.angle;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isAlive()) {
            this.setSpriteForAge(sprite);

            float alpha = ((float) this.age / (float) this.maxAge);

            if (this.animScale) {
                this.scale = MysticalUtils.tweenHandling(this.baseScale, this.targetScale, alpha);
            }
            if (this.animAngle) {
                this.prevAngle = this.angle;
                this.angle = MysticalUtils.tweenHandling(this.baseAngle, this.targetAngle, alpha);
            }
        }



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
    public record Factory(SpriteProvider spriteProvider, ParticleCreator.Finalized creator) implements ParticleFactory<SimpleParticleType> {
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientWorld clientWorld, double x, double y, double z, double vx, double vy, double vz) {
            return new BasicMysticalParticle(clientWorld, x, y, z, vx, vy, vz, spriteProvider, creator);
        }
    }
}
