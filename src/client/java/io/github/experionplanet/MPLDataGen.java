package io.github.experionplanet;

import io.github.experionplanet.DataGens.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class MPLDataGen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModelDataGen::new);
		pack.addProvider(TranslateDataGen::new);
		pack.addProvider(BlockLootableDataGen::new);
		pack.addProvider(ItemTagsDataGen::new);
		pack.addProvider(BlockTagsDataGen::new);
		pack.addProvider(BiomeTagsDataGen::new);
	}
}
