package io.github.experionplanet.mysticalplantslg.worldgen;

import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import io.github.experionplanet.mysticalplantslg.worldgen.configuredfeature.BindingRockFeatureConfig;
import io.github.experionplanet.mysticalplantslg.worldgen.feature.BindingRockFeature;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.feature.Feature;

public class MPLFeatures {
    public static final Feature<BindingRockFeatureConfig> BINDING_ROCK = Registry.register(Registries.FEATURE, MysticalUtils.newId("binding_rock"), new BindingRockFeature(BindingRockFeatureConfig.CODEC));

    public static void init() {

    }
}
