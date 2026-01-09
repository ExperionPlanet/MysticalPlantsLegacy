package io.github.experionplanet.mysticalplantslg.worldgen;

import io.github.experionplanet.mysticalplantslg.blocks.custom.*;
import io.github.experionplanet.mysticalplantslg.init.MPLBlockProperties;
import io.github.experionplanet.mysticalplantslg.init.MPLBlocks;
import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.collection.DataPool;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import java.util.List;

public class MPLConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?,?>> EXP_MUSHROOMS_KEY = registerKey("exp_mushrooms");
    public static final RegistryKey<ConfiguredFeature<?,?>> EXP_PLANTS_KEY = registerKey("exp_plants");

    public static final RegistryKey<ConfiguredFeature<?,?>> FROST_PLANTS_KEY = registerKey("frost_plants");

    public static final RegistryKey<ConfiguredFeature<?,?>> BOGGED_PLANTS_KEY = registerKey("bogged_plants");
    public static final RegistryKey<ConfiguredFeature<?,?>> DISGUISE_ORCHIDS_KEY = registerKey("disguise_orchids");

    public static final RegistryKey<ConfiguredFeature<?,?>> SOUL_PLANTS_KEY = registerKey("soul_plants");
    public static final RegistryKey<ConfiguredFeature<?,?>> SOUL_BELL_KEY = registerKey("soul_pitchers");

    public static final RegistryKey<ConfiguredFeature<?,?>> VOID_PLANTS = registerKey("void_plants");

    public static final RegistryKey<ConfiguredFeature<?,?>> MYSTICAL_ORE_KEY = registerKey("mystical_ore_key");

    public static void boot(Registerable<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        registerPatch(context, EXP_MUSHROOMS_KEY, poolBuildOf()
                        .add(MPLBlocks.SMALL_EXP_MUSHROOMS.getDefaultState(), 6)
                        .add(MPLBlocks.MEDIUM_EXP_MUSHROOMS.getDefaultState(), 4)
                        .add(MPLBlocks.LARGE_EXP_MUSHROOMS.getDefaultState(), 2)
        );
        registerPatch(context, EXP_PLANTS_KEY, poolBuildOf()
                .add(MPLBlocks.EXBISCUS.getDefaultState(), 10)
                .add(MPLBlocks.BLEEDING_EXP.getDefaultState(), 6)
                .add(MPLBlocks.EXBISCUS.getDefaultState().with(ExbiscusBlock.BLOOMING, true), 1)
        );
        registerPatch(context, FROST_PLANTS_KEY, poolBuildOf()
                .add(MPLBlocks.FROST_UMBRELLA_FLOWER.getDefaultState(), 10)
                .add(MPLBlocks.GLACIER_PASSION_FLOWER.getDefaultState(), 5)
                .add(MPLBlocks.PERMAFROST_SHROOM.getDefaultState(), 25)
                .add(MPLBlocks.GLACIER_PASSION_FLOWER.getDefaultState().with(GlacierPassionFlowerBlock.BLOOMING, true), 1)
        );
        registerPatch(context, BOGGED_PLANTS_KEY, poolBuildOf()
                .add(MPLBlocks.BOGSPORE_CAP.getDefaultState(), 30)
                .add(MPLBlocks.HUNGERBALM.getDefaultState(), 6)
                .add(MPLBlocks.HUNGERBALM.getDefaultState().with(HungerbalmBlock.BLOOMING, true), 1)
        );
        registerSinglePatch(context, DISGUISE_ORCHIDS_KEY, MPLBlocks.DISGUISE_ORCHID.getDefaultState());

        registerPatch(context, SOUL_PLANTS_KEY, poolBuildOf()
                .add(MPLBlocks.SOUL_PITCHER.getDefaultState(), 40)
                .add(MPLBlocks.SOUL_PITCHER.getDefaultState().with(MPLBlockProperties.BLOOMING, true), 10)
                .add(MPLBlocks.SOUL_POSSESSION_IRIS.getDefaultState(), 6)
        );

        registerSingle(context, SOUL_BELL_KEY, MPLBlocks.SOUL_BELL.getDefaultState());

        registerPatch(context, VOID_PLANTS, poolBuildOf()
                .add(MPLBlocks.VOID_CAP.getDefaultState(), 40)
                .add(MPLBlocks.VOID_STRAWFLOWER.getDefaultState(), 5)
                .add(MPLBlocks.SHULKURA.getDefaultState(), 10)
                .add(MPLBlocks.SHULKURA.getDefaultState().with(ShulkuraBlock.FLOWER_AMOUNT, 2), 10)
                .add(MPLBlocks.SHULKURA.getDefaultState().with(ShulkuraBlock.FLOWER_AMOUNT, 3), 10)
        );

        register(context, MYSTICAL_ORE_KEY, Feature.ORE, new OreFeatureConfig(
                List.of(
                        OreFeatureConfig.createTarget(stoneReplaceables, MPLBlocks.MYSTICAL_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables, MPLBlocks.DEEPSLATE_MYSTICAL_ORE.getDefaultState())
                ),
                6
        ));
    }

    public static void registerSingle(Registerable<ConfiguredFeature<?, ?>> context, RegistryKey<ConfiguredFeature<?, ?>> key, BlockState state) {
        register(context, key, Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(state)));
    }

    public static void registerSinglePatch(Registerable<ConfiguredFeature<?, ?>> context, RegistryKey<ConfiguredFeature<?, ?>> key, BlockState state) {
        register(context, key, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(state))));
    }

    public static void registerPatch(Registerable<ConfiguredFeature<?, ?>> context, RegistryKey<ConfiguredFeature<?, ?>> key, DataPool.Builder<BlockState> pool) {
        register(context, key, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(pool))));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, MysticalUtils.newId(name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context, RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

    private static DataPool.Builder<BlockState> poolBuildOf() {
        return DataPool.builder();
    }

}
