package io.github.experionplanet.utils;

import io.github.experionplanet.MPLMain;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class ExperionUtils {
    public static Identifier newId(String str) {
        return Identifier.of(MPLMain.MOD_ID, str);
    }

    public static Vec3d v3dConvert(BlockPos pos, boolean middlePoint) {
        double addings = 0d;

        if (middlePoint) {
            addings = 0.5d;
        }

        return new Vec3d(pos.getX() + addings, pos.getY() + addings, pos.getZ() + addings);
    }

    public static float floatInRange(Random random, float min, float max) {
        return min + random.nextFloat() * (max - min);
    }
}
