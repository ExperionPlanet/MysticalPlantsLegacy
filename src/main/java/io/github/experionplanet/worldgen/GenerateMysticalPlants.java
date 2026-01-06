package io.github.experionplanet.worldgen;

import io.github.experionplanet.init.MPLBiomeTags;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.function.Predicate;

public class GenerateMysticalPlants {
    public static void boot() {
        // Experiences
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.DARK_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION,
                MPLPlacedFeatures.EXP_MUSHROOMS_KEY
        );
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.DARK_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION,
                MPLPlacedFeatures.EXP_PLANTS_KEY
        );
        BiomeModifications.addFeature(
                BiomeSelectors
                        .includeByKey(BiomeKeys.SNOWY_PLAINS, BiomeKeys.SNOWY_TAIGA, BiomeKeys.SNOWY_SLOPES)
                        .and(BiomeSelectors.tag(MPLBiomeTags.C_IS_SNOWY_PLAINS)).and(BiomeSelectors.tag(MPLBiomeTags.C_IS_SNOWY))
                ,
                GenerationStep.Feature.VEGETAL_DECORATION,
                MPLPlacedFeatures.FROST_PLANTS_KEY
        );
        BiomeModifications.addFeature(
                BiomeSelectors
                        .includeByKey(BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP)
                        .and(BiomeSelectors.tag(MPLBiomeTags.C_IS_SWAMP))
                ,
                GenerationStep.Feature.VEGETAL_DECORATION,
                MPLPlacedFeatures.BOGGED_PLANTS_KEY
        );
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.SWAMP),
                GenerationStep.Feature.VEGETAL_DECORATION,
                MPLPlacedFeatures.DISGUISE_ORCHIDS_KEY
        );
        BiomeModifications.addFeature(
                BiomeSelectors.tag(MPLBiomeTags.SOUL),
                GenerationStep.Feature.VEGETAL_DECORATION,
                MPLPlacedFeatures.SOUL_PLANTS_KEY
        );
        BiomeModifications.addFeature(
                BiomeSelectors.tag(MPLBiomeTags.SOUL),
                GenerationStep.Feature.VEGETAL_DECORATION,
                MPLPlacedFeatures.SOUL_BELL_KEY
        );
    }

}
