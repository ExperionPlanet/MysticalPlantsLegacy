package io.github.experionplanet.mysticalplantslg.worldgen.feature;

import com.mojang.serialization.Codec;
import io.github.experionplanet.mysticalplantslg.blocks.custom.BindingRockBlock;
import io.github.experionplanet.mysticalplantslg.blocks.entity.custom.BindingRockBlockEntity;
import io.github.experionplanet.mysticalplantslg.init.MPLBlocks;
import io.github.experionplanet.mysticalplantslg.utils.ExperionLogger;
import io.github.experionplanet.mysticalplantslg.worldgen.configuredfeature.BindingRockFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class BindingRockFeature extends Feature<BindingRockFeatureConfig> {
    public BindingRockFeature(Codec<BindingRockFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<BindingRockFeatureConfig> context) {
        BindingRockFeatureConfig config = context.getConfig();
        BlockPos origin = context.getOrigin();
        StructureWorldAccess world = context.getWorld();
        Random rand = world.getRandom();

        BlockPos p1 = origin.add(-2,0,-2);
        BlockPos p2 = origin.add(2,2,2);

        Box box = new Box(p1.getX(), p1.getY(), p1.getZ(), p2.getX(), p2.getY(), p2.getZ());
        BlockState groundState = world.getBlockState(origin.down());

        if (world.getStatesInBox(box).allMatch(state -> state.isIn(BlockTags.AIR)) && groundState.isIn(BlockTags.DIRT)) {
            world.setBlockState(origin, MPLBlocks.BINDING_ROCK.getDefaultState().with(BindingRockBlock.ROCK_SKIN,config.skin()),2);

            BlockPos.Mutable mutable = new BlockPos.Mutable(origin.getX(), origin.getY(), origin.getZ());

            int r = config.range();
            int spreadY = 1;

            for (int a = 0; a < 96; a++) {
                mutable.set(
                        origin,
                        rand.nextInt(config.range() * 2 + 1) - config.range(),
                        rand.nextInt(spreadY * 2 + 1) - spreadY,
                        rand.nextInt(config.range() * 2 + 1) - config.range()
                );

                if (!world.isAir(mutable)) continue;

                BlockState state = config.provider().get(rand, mutable);

                if (!state.canPlaceAt(world, mutable)) continue;

                world.setBlockState(mutable, state, 2);


            }

            ExperionLogger.Print("Generate at " + origin.getX() + " " + origin.getY() + " " + origin.getZ());

            return true;
        }

        return false;
    }
}
