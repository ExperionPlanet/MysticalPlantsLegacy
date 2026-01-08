package io.github.experionplanet.particle;

import io.github.experionplanet.utils.MysticalUtils;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.util.math.random.Random;

import java.util.ArrayList;
import java.util.List;

public class ParticleCreator {
    private final RangeVal<Integer> age;
    private final RangeVal<Float> scale;
    private float velocityMultiplier = 0f;
    private boolean ascending = false;
    private RangeVal<Double> veloX = new RangeVal<>(0d, 0d);
    private RangeVal<Double> veloY = new RangeVal<>(0d, 0d);
    private RangeVal<Double> veloZ = new RangeVal<>(0d, 0d);
    private float gravityStrength = 0f;
    private final ParticleTextureSheet particleTextureSheet;
    private RangeVal<Float> targetScale = new RangeVal<>(0f, 0f);
    private float angle = 0f;
    private RangeVal<Float> targetAngle = new RangeVal<>(0f, 0f);
    private List<Props> props = new ArrayList<>();

    public ParticleCreator(int minAge, int maxAge, float minScale, float maxScale, ParticleTextureSheet particleTextureSheet) {
        this.age = new RangeVal<>(minAge, maxAge);
        this.scale = new RangeVal<>(minScale, maxScale);
        this.particleTextureSheet = particleTextureSheet;
    }

    public record RangeVal<T>(T min, T max) {
        public int getInt(Random rand) {
            return rand.nextBetween((Integer) min, (Integer) max);
        }

        public float getFloat(Random rand) {
            return MysticalUtils.floatInRange(rand, (Float) min, (Float) max);
        }

        public double getDouble(Random rand) {
            return MysticalUtils.doubleInRange(rand, (Double) min, (Double) max);
        }

    }

    public enum Props {
        VELOCITY_MULTIPLIER,
        GRAVITY_STRENGTH,
        VELO_X,
        VELO_Y,
        VELO_Z,
        TARGET_SCALE,
        TARGET_ANGLE
    }

    public class Finalized {
        public final RangeVal<Integer> age;
        public final RangeVal<Float> scale;
        public final float velocityMultiplier;
        public final boolean ascending;
        public final RangeVal<Double> veloX;
        public final RangeVal<Double> veloY;
        public final RangeVal<Double> veloZ;
        public final float gravityStrength;
        public final ParticleTextureSheet particleTextureSheet;
        public final RangeVal<Float> targetScale;
        public final float angle;
        public final RangeVal<Float> targetAngle;
        public final List<Props> props;

        public Finalized(RangeVal<Integer> age, RangeVal<Float> scale, float velocityMultiplier, boolean ascending, RangeVal<Double> veloX, RangeVal<Double> veloY, RangeVal<Double> veloZ, float gravityStrength, ParticleTextureSheet particleTextureSheet, RangeVal<Float> scaleAdds, float angle, RangeVal<Float> angleAdds, List<Props> props) {
            this.age = age;
            this.scale = scale;
            this.velocityMultiplier = velocityMultiplier;
            this.ascending = ascending;
            this.veloX = veloX;
            this.veloY = veloY;
            this.veloZ = veloZ;
            this.gravityStrength = gravityStrength;
            this.particleTextureSheet = particleTextureSheet;
            this.targetScale = scaleAdds;
            this.angle = angle;
            this.targetAngle = angleAdds;
            this.props = props;
        }
    }

    public ParticleCreator velocityMultiplier(float v) {
        this.velocityMultiplier = v;
        this.props.add(Props.VELOCITY_MULTIPLIER);
        return this;
    }
    public ParticleCreator gravityStrength(float v) {
        this.gravityStrength = v;
        this.props.add(Props.GRAVITY_STRENGTH);
        return this;
    }
    public ParticleCreator targetAngle(float v1, float v2) {
        this.targetAngle = new RangeVal<>(v1, v2);
        this.props.add(Props.TARGET_ANGLE);
        return this;
    }
    public ParticleCreator angle(float v) {
        this.angle = v;
        return this;
    }
    public ParticleCreator targetScale(float v1, float v2) {
        this.targetScale = new RangeVal<>(v1, v2);
        this.props.add(Props.TARGET_SCALE);
        return this;
    }
    public ParticleCreator ascending() {
        this.ascending = true;
        return this;
    }
    public ParticleCreator veloX(double v1, double v2) {
        this.veloX = new RangeVal<>(v1, v2);
        this.props.add(Props.VELO_X);
        return this;
    }
    public ParticleCreator veloY(double v1, double v2) {
        this.veloY = new RangeVal<>(v1, v2);
        this.props.add(Props.VELO_Y);
        return this;
    }
    public ParticleCreator veloZ(double v1, double v2) {
        this.veloZ = new RangeVal<>(v1, v2);
        this.props.add(Props.VELO_Z);
        return this;
    }
    public Finalized export() {
        return new Finalized(
                this.age,
                this.scale,
                this.velocityMultiplier,
                this.ascending,
                this.veloX,
                this.veloY,
                this.veloZ,
                this.gravityStrength,
                this.particleTextureSheet,
                this.targetScale,
                this.angle,
                this.targetAngle,
                this.props
        );
    }


}
