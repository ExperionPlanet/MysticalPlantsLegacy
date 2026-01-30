package io.github.experionplanet.mysticalplantslg.worldgen;

import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class MPLPlacedFeatures {
    public static final RegistryKey<PlacedFeature> EXP_MUSHROOMS_KEY = registerKey("exp_mushrooms");
    public static final RegistryKey<PlacedFeature> EXP_PLANTS_KEY = registerKey("exp_plants");
    public static final RegistryKey<PlacedFeature> FROST_PLANTS_KEY = registerKey("frost_plants");
    public static final RegistryKey<PlacedFeature> BOGGED_PLANTS_KEY = registerKey("bogged_plants");
    public static final RegistryKey<PlacedFeature> DISGUISE_ORCHIDS_KEY = registerKey("disguise_orchids");
    public static final RegistryKey<PlacedFeature> SOUL_PLANTS_KEY = registerKey("soul_plants");
    public static final RegistryKey<PlacedFeature> SOUL_BELL_KEY = registerKey("soul_pitchers");
    public static final RegistryKey<PlacedFeature> VOID_PLANTS_KEY = registerKey("void_plants");

    public static final RegistryKey<PlacedFeature> MYSTICAL_ORE_KEY = registerKey("mystical_ore");

    public static final RegistryKey<PlacedFeature> EXPERIENCE_PICKAXE_BINDING_ROCK_KEY = registerKey("experience_pickaxe_binding_rock");
    public static final RegistryKey<PlacedFeature> FROST_AXE_BINDING_ROCK_KEY = registerKey("frost_axe_binding_rock");
    public static final RegistryKey<PlacedFeature> BOGGED_SHOVEL_BINDING_ROCK_KEY = registerKey("bogged_shovel_binding_rock");
    public static final RegistryKey<PlacedFeature> SOUL_HOE_BINDING_ROCK_KEY = registerKey("soul_hoe_binding_rock");
    public static final RegistryKey<PlacedFeature> VOID_SWORD_BINDING_ROCK_KEY = registerKey("void_sword_binding_rock");

    public static void boot(Registerable<PlacedFeature> context) {
        var configuredFeatures = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        registerMysticalPatches(context, EXP_MUSHROOMS_KEY, configuredFeatures.getOrThrow(MPLConfiguredFeatures.EXP_MUSHROOMS_KEY), 4);
        registerMysticalPatches(context, EXP_PLANTS_KEY, configuredFeatures.getOrThrow(MPLConfiguredFeatures.EXP_PLANTS_KEY), 24);

        registerMysticalPatches(context, FROST_PLANTS_KEY, configuredFeatures.getOrThrow(MPLConfiguredFeatures.FROST_PLANTS_KEY), 20);

        registerMysticalPatches(context, BOGGED_PLANTS_KEY, configuredFeatures.getOrThrow(MPLConfiguredFeatures.BOGGED_PLANTS_KEY), 20);
        registerMysticalPatches(context, DISGUISE_ORCHIDS_KEY, configuredFeatures.getOrThrow(MPLConfiguredFeatures.DISGUISE_ORCHIDS_KEY), 32);

        register(context, SOUL_PLANTS_KEY, configuredFeatures.getOrThrow(MPLConfiguredFeatures.SOUL_PLANTS_KEY),
                RarityFilterPlacementModifier.of(3),
                SquarePlacementModifier.of(),
                BiomePlacementModifier.of(),
                PlacedFeatures.BOTTOM_TO_TOP_RANGE
        );
        register(context, SOUL_BELL_KEY, configuredFeatures.getOrThrow(MPLConfiguredFeatures.SOUL_BELL_KEY),
                RarityFilterPlacementModifier.of(5),
                SquarePlacementModifier.of(),
                BiomePlacementModifier.of(),
                PlacedFeatures.BOTTOM_TO_TOP_RANGE
        );

        registerMysticalPatches(context, VOID_PLANTS_KEY, configuredFeatures.getOrThrow(MPLConfiguredFeatures.VOID_PLANTS), 20);

        register(context, MYSTICAL_ORE_KEY, configuredFeatures.getOrThrow(MPLConfiguredFeatures.MYSTICAL_ORE_KEY),
                HeightRangePlacementModifier.trapezoid(YOffset.fixed(-60), YOffset.fixed(10)),
                RarityFilterPlacementModifier.of(2),
                CountPlacementModifier.of(4),
                SquarePlacementModifier.of(),
                BiomePlacementModifier.of()
        );
        register(context, EXPERIENCE_PICKAXE_BINDING_ROCK_KEY, configuredFeatures.getOrThrow(MPLConfiguredFeatures.EXPERIENCE_PICKAXE_BINDING_ROCK_KEY),
                RarityFilterPlacementModifier.of(5),
                SquarePlacementModifier.of(),
                BiomePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP
        );

        register(context, FROST_AXE_BINDING_ROCK_KEY, configuredFeatures.getOrThrow(MPLConfiguredFeatures.FROST_AXE_BINDING_ROCK_KEY),
                RarityFilterPlacementModifier.of(32),
                SquarePlacementModifier.of(),
                BiomePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP
        );

        register(context, BOGGED_SHOVEL_BINDING_ROCK_KEY, configuredFeatures.getOrThrow(MPLConfiguredFeatures.BOGGED_SHOVEL_BINDING_ROCK_KEY),
                RarityFilterPlacementModifier.of(15),
                SquarePlacementModifier.of(),
                BiomePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP
        );

        register(context, SOUL_HOE_BINDING_ROCK_KEY, configuredFeatures.getOrThrow(MPLConfiguredFeatures.SOUL_HOE_BINDING_ROCK_KEY),
                RarityFilterPlacementModifier.of(64),
                SquarePlacementModifier.of(),
                BiomePlacementModifier.of(),
                PlacedFeatures.BOTTOM_TO_TOP_RANGE
        );

        register(context, VOID_SWORD_BINDING_ROCK_KEY, configuredFeatures.getOrThrow(MPLConfiguredFeatures.VOID_SWORD_BINDING_ROCK_KEY),
                RarityFilterPlacementModifier.of(400),
                SquarePlacementModifier.of(),
                BiomePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP
        );




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
