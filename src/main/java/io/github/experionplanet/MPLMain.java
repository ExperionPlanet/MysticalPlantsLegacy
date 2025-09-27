package io.github.experionplanet;

import io.github.experionplanet.init.MPLBlockEntities;
import io.github.experionplanet.init.MPLBlocks;
import io.github.experionplanet.init.MPLItemGroup;
import io.github.experionplanet.init.MPLItems;
import io.github.experionplanet.utils.ExperionLogger;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MPLMain implements ModInitializer {
	public static final String MOD_ID = "mysticalplantslg";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static boolean onDev = false;

	@Override
	public void onInitialize() {
		MPLItems.init();
		MPLItemGroup.init();
		MPLBlocks.init();
		MPLBlockEntities.init();

		if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
			onDev = true;
			ExperionLogger.Print("Development Enviroment Detected!");
		}

		ExperionLogger.Print("Fully Initiated!");
	}
}