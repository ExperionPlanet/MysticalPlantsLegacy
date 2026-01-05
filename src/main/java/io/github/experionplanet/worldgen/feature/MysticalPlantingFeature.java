package io.github.experionplanet.worldgen.feature;

import com.mojang.serialization.Codec;
import io.github.experionplanet.worldgen.configuredfeature.MysticalPlantingConfig;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class MysticalPlantingFeature extends Feature<MysticalPlantingConfig> {
    public MysticalPlantingFeature(Codec<MysticalPlantingConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<MysticalPlantingConfig> context) {
        StructureWorldAccess world = context.getWorld();
        MysticalPlantingConfig config = context.getConfig();
        BlockPos origin = context.getOrigin();

        Random rand = world.getRandom();
        BlockState blockState = Registries.BLOCK.get(config.blockIds().get(rand.nextBetween(0, config.blockIds().size() - 1))).getDefaultState();



        return false;
    }
}
