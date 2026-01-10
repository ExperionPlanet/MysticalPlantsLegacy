package io.github.experionplanet.mysticalplantslg.worldgen;

import io.github.experionplanet.mysticalplantslg.init.MPLBiomeTags;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

public class MPLBiomeModifications {
    public static void boot() {
        // Plants
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
        BiomeModifications.addFeature(
                BiomeSelectors.tag(BiomeTags.IS_END),
                GenerationStep.Feature.VEGETAL_DECORATION,
                MPLPlacedFeatures.VOID_PLANTS_KEY
        );
        // Ores
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES,
                MPLPlacedFeatures.MYSTICAL_ORE_KEY);
        // Binding Rock
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.DARK_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION,
                MPLPlacedFeatures.EXPERIENCE_PICKAXE_BINDING_ROCK_KEY
        );
    }

}
