package io.github.experionplanet.registry;

import net.minecraft.util.math.Vec3d;

public class TrxContent {
    public Vec3d translate;

    public float rx = 0f;
    public float ry = 0f;
    public float rz = 0f;

    public boolean canRX = true;
    public boolean canRY = true;
    public boolean canRZ = true;

    public TrxContent(double x, double y, double z, float rx, float ry, float rz) {
        this.rz = rz;
        this.ry = ry;
        this.rx = rx;
        this.translate = new Vec3d(x, y ,z);
    }

    public TrxContent(double x, double y, double z, float rz) {
        this.translate = new Vec3d(x, y, z);
        this.rz = rz;
    }

    public TrxContent disableRX() {
        this.canRX = false;
        return this;
    }
    public TrxContent disableRY() {
        this.canRY = false;
        return this;
    }
    public TrxContent disableRZ() {
        this.canRZ = false;
        return this;
    }
}
