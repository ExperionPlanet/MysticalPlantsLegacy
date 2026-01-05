package io.github.experionplanet.worldgen;

import io.github.experionplanet.utils.MysticalUtils;
import io.github.experionplanet.worldgen.configuredfeature.MysticalPlantingConfig;
import io.github.experionplanet.worldgen.feature.MysticalPlantingFeature;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.feature.Feature;

public class MPLFeatures {
    public static final Feature<MysticalPlantingConfig> MYSTICAL_PLANTING = Registry.register(Registries.FEATURE, MysticalUtils.newId("mystical_planting"), new MysticalPlantingFeature(MysticalPlantingConfig.CODEC));

    public static void init() {

    }
}
