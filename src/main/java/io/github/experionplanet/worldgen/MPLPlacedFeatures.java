package io.github.experionplanet.worldgen;

import io.github.experionplanet.utils.MysticalUtils;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

import java.util.List;

public class MPLPlacedFeatures {
    public static final RegistryKey<PlacedFeature> EXP_MUSHROOMS_KEY = registerKey("exp_mushrooms");
    public static final RegistryKey<PlacedFeature> EXP_PLANTS_KEY = registerKey("exp_plants");

    public static void boot(Registerable<PlacedFeature> context) {
        var configuredFeatures = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        registerMysticalPatches(context, EXP_MUSHROOMS_KEY, configuredFeatures.getOrThrow(MPLConfiguredFeatures.EXP_MUSHROOMS_KEY), 4);
        registerMysticalPatches(context, EXP_PLANTS_KEY, configuredFeatures.getOrThrow(MPLConfiguredFeatures.EXP_PLANTS_KEY), 10);

    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, MysticalUtils.newId(name));
    }

    private static void registerMysticalPatches(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration, int chances) {
        register(context, key, configuration, RarityFilterPlacementModifier.of(chances), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key,
                                                                                   RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                                                                   PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }

}
