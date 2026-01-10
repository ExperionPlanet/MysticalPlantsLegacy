package io.github.experionplanet.mysticalplantslg.worldgen.configuredfeature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.Direction;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public record BindingRockFeatureConfig(int skin, Identifier itemID, int range, int amount,BlockStateProvider provider) implements FeatureConfig {
    public static final Codec<BindingRockFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            Codecs.POSITIVE_INT.fieldOf("skin").forGetter(BindingRockFeatureConfig::skin),
                            Identifier.CODEC.fieldOf("item_id").forGetter(BindingRockFeatureConfig::itemID),
                            Codecs.POSITIVE_INT.fieldOf("range").forGetter(BindingRockFeatureConfig::range),
                            Codecs.POSITIVE_INT.fieldOf("amount").forGetter(BindingRockFeatureConfig::amount),
                            BlockStateProvider.TYPE_CODEC.fieldOf("to_place").forGetter(BindingRockFeatureConfig::provider)

                    )
                    .apply(instance, BindingRockFeatureConfig::new));

    public static BindingRockFeatureConfig of(int skin, Item item, int radius, int amount,BlockStateProvider provider) {
        return new BindingRockFeatureConfig(skin, Registries.ITEM.getId(item), radius, amount, provider);
    }

}
