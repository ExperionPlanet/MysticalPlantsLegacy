package io.github.experionplanet.worldgen;

import io.github.experionplanet.init.MPLBlocks;
import io.github.experionplanet.utils.MysticalUtils;
import io.github.experionplanet.worldgen.configuredfeature.MysticalPlantingConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.NoiseBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import java.util.List;

public class MPLConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?,?>> EXP_MUSHROOMS_KEY = registerKey("exp_mushrooms");
    public static final RegistryKey<ConfiguredFeature<?,?>> EXP_PLANTS_KEY = registerKey("exp_plants");

    public static void boot(Registerable<ConfiguredFeature<?, ?>> context) {

        register(context, EXP_MUSHROOMS_KEY, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(
                                new WeightedBlockStateProvider(
                                        DataPool.<BlockState>builder()
                                                .add(MPLBlocks.SMALL_EXP_MUSHROOMS.getDefaultState(), 6)
                                                .add(MPLBlocks.MEDIUM_EXP_MUSHROOMS.getDefaultState(), 4)
                                                .add(MPLBlocks.LARGE_EXP_MUSHROOMS.getDefaultState(), 2)
                                )
                        )
                )
        );
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, MysticalUtils.newId(name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context, RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
