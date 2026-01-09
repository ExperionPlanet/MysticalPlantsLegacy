package io.github.experionplanet.mysticalplantslg.utils;

import io.github.experionplanet.mysticalplantslg.MPLMain;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

import java.util.ArrayList;
import java.util.List;

public class MysticalUtils {
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

    public static double doubleInRange(Random random, double min, double max) {
        return min + random.nextDouble() * (max - min);
    }

    public static float alphaHandling(float progress, float target) {
        return Math.min(progress / target, 1);
    }

    public static float tweenHandling(float start, float target, float alpha) {
        return start + (target - start) * alpha;
    }

    public static Identifier getItemID(Item item) {
        return Registries.ITEM.getId(item);
    }

    public static Identifier getBlockID(Block block) {
        return Registries.BLOCK.getId(block);
    }

    public static List<Identifier> blockToID(Block... list) {
        List<Identifier> res = new ArrayList<>();
        for (Block block : list) {
            res.add(Registries.BLOCK.getId(block));
        }

        return res;
    }
}
