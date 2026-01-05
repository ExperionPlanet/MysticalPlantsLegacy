package io.github.experionplanet.worldgen.configuredfeature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.List;

public record MysticalPlantingConfig(int minAmount, int maxAmount, List<Identifier> blockIds) implements FeatureConfig {
    public static final Codec<MysticalPlantingConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codecs.POSITIVE_INT.fieldOf("min").forGetter(MysticalPlantingConfig::minAmount),
                    Codecs.POSITIVE_INT.fieldOf("max").forGetter(MysticalPlantingConfig::maxAmount),
                    Identifier.CODEC.listOf().fieldOf("blockids").forGetter(MysticalPlantingConfig::blockIds)
            ).apply(instance, MysticalPlantingConfig::new));
}
