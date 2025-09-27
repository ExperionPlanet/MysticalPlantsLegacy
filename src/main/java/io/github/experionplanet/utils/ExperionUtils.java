package io.github.experionplanet.utils;

import io.github.experionplanet.MPLMain;
import net.minecraft.util.Identifier;

public class ExperionUtils {
    public static Identifier newId(String str) {
        return Identifier.of(MPLMain.MOD_ID, str);
    }
}
