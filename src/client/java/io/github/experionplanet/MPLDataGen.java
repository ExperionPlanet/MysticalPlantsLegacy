package io.github.experionplanet;

import io.github.experionplanet.DataGens.ModelDataGen;
import io.github.experionplanet.DataGens.TranslateDataGen;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class MPLDataGen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModelDataGen::new);
		pack.addProvider(TranslateDataGen::new);
	}
}
