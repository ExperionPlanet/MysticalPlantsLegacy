package io.github.experionplanet.mysticalplantslg;

import io.github.experionplanet.mysticalplantslg.DataGens.*;
import io.github.experionplanet.mysticalplantslg.worldgen.MPLConfiguredFeatures;
import io.github.experionplanet.mysticalplantslg.worldgen.MPLPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

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
		pack.addProvider(FabricRegistryDataGen::new);
		pack.addProvider(RecipeDataGen::new);
		pack.addProvider(ChestLootableDataGen::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, MPLConfiguredFeatures::boot);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, MPLPlacedFeatures::boot);
	}


}
