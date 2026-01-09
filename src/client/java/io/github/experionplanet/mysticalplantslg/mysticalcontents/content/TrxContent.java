package io.github.experionplanet.mysticalplantslg.mysticalcontents.content;

import net.minecraft.util.math.Vec3d;

public class TrxContent {
    public Vec3d translate;

    public final float rx;
    public final float ry;
    public final float rz;

    public TrxContent(double x, double y, double z, float rx, float ry, float rz) {
        this.rz = rz;
        this.ry = ry;
        this.rx = rx;
        this.translate = new Vec3d(x, y ,z);
    }

    public TrxContent(double x, double y, double z, float rz) {
        this.translate = new Vec3d(x, y, z);
        this.rx = 0;
        this.ry = 0;
        this.rz = rz;
    }

}
