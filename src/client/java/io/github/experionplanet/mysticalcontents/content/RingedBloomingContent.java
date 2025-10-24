package io.github.experionplanet.mysticalcontents.content;

import net.minecraft.util.math.Vec3d;

public class RingedBloomingContent {
    public final double x;
    public final double y;
    public final double z;
    public final float speed;
    public final String texture;
    public final boolean floatAnim;
    public final boolean lowFPS;

    public RingedBloomingContent(double x, double y, double z, float speed, String texture, boolean floatAnim, boolean lowFPS) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.speed = speed;
        this.texture = texture;
        this.floatAnim = floatAnim;
        this.lowFPS = lowFPS;
    }
}
