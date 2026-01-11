package io.github.experionplanet.mysticalplantslg.DataGens;

import io.github.experionplanet.mysticalplantslg.init.MPLBiomeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BiomeTags;
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
        getOrCreateTagBuilder(MPLBiomeTags.C_IS_SNOWY);
        getOrCreateTagBuilder(MPLBiomeTags.C_IS_SNOWY_PLAINS);
        getOrCreateTagBuilder(MPLBiomeTags.C_IS_SWAMP);
        getOrCreateTagBuilder(MPLBiomeTags.EXPERIENCE_PICKAXE_SPAWNABLE).add(BiomeKeys.DARK_FOREST);
        getOrCreateTagBuilder(MPLBiomeTags.FROST_AXE_SPAWNABLE).addTag(MPLBiomeTags.C_IS_SNOWY).addTag(MPLBiomeTags.C_IS_SNOWY_PLAINS).add(BiomeKeys.SNOWY_PLAINS, BiomeKeys.SNOWY_TAIGA, BiomeKeys.SNOWY_SLOPES);
        getOrCreateTagBuilder(MPLBiomeTags.BOGGED_SHOVEL_SPAWNABLE).addTag(MPLBiomeTags.C_IS_SWAMP).add(BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP);
        getOrCreateTagBuilder(MPLBiomeTags.SOUL_HOE_SPAWNABLE).add(BiomeKeys.SOUL_SAND_VALLEY);
        getOrCreateTagBuilder(BiomeTags.IS_END);
        getOrCreateTagBuilder(MPLBiomeTags.VOID_SWORD_SPAWNABLE).addTag(BiomeTags.IS_END);
    }
}
