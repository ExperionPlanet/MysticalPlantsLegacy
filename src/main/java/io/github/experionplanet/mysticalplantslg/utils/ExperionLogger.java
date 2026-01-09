package io.github.experionplanet.mysticalplantslg.utils;

import io.github.experionplanet.mysticalplantslg.MPLMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Custom Logger
public class ExperionLogger {
    public static final Logger LOGGER = LoggerFactory.getLogger("ExpDev/" + MPLMain.MOD_ID);

    public static boolean isOnDev() {
        return MPLMain.onDev;
    }

    public static void Print(String str) {
        if (isOnDev()) {
            LOGGER.info(str);
        }

    }

    public static void Warn(String str) {
        if (isOnDev()) {
            LOGGER.warn(str);
        }
    }
}
