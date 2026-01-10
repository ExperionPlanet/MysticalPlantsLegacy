package io.github.experionplanet.mysticalplantslg.DataGens;

import io.github.experionplanet.mysticalplantslg.init.MPLBiomeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.concurrent.CompletableFuture;

public class BiomeTagsDataGen extends FabricTagProvider<Biome> {
    public BiomeTagsDataGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.BIOME, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(MPLBiomeTags.SOUL).add(BiomeKeys.SOUL_SAND_VALLEY);
        getOrCreateTagBuilder(MPLBiomeTags.EXPERIENCE_PICKAXE_SPAWNABLE).add(BiomeKeys.DARK_FOREST);
        getOrCreateTagBuilder(MPLBiomeTags.C_IS_SNOWY);
        getOrCreateTagBuilder(MPLBiomeTags.C_IS_SNOWY_PLAINS);
        getOrCreateTagBuilder(MPLBiomeTags.C_IS_SWAMP);
    }
}
