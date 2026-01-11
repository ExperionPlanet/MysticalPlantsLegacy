package io.github.experionplanet.mysticalplantslg.worldgen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import io.github.experionplanet.mysticalplantslg.blocks.custom.BindingRockBlock;
import io.github.experionplanet.mysticalplantslg.init.MPLBlocks;
import io.github.experionplanet.mysticalplantslg.utils.ExperionLogger;
import io.github.experionplanet.mysticalplantslg.worldgen.configuredfeature.BindingRockFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class BindingRockFeature extends Feature<BindingRockFeatureConfig> {
    private static final ImmutableList<Integer> INT_DIR = ImmutableList.of(-1, 1);
    private static final int MAX_RANGE = 100;

    public BindingRockFeature(Codec<BindingRockFeatureConfig> configCodec) {
        super(configCodec);
    }

    private static boolean checkState(BlockState state) {
        if (state.getFluidState().isEmpty()) {
            return state.isAir() || state.isReplaceable();
        }

        return false;
    }

    private static boolean checkGroundState(BlockState state, BlockPos pos, StructureWorldAccess world) {
        return state.isSolidBlock(world, pos) && state.getFluidState().isEmpty();
    }

    private static boolean checkBoxState(BlockPos origin, StructureWorldAccess world, int range) {
        BlockPos p1 = origin.add(-range,0,-range);
        BlockPos p2 = origin.add(range,range,range);

        Box box1 = new Box(p1.getX(), p1.getY(), p1.getZ(), p2.getX(), p2.getY(), p2.getZ());

        return world.getStatesInBox(box1).allMatch(BindingRockFeature::checkState);
    }

    @Override
    public boolean generate(FeatureContext<BindingRockFeatureConfig> context) {
        BindingRockFeatureConfig config = context.getConfig();
        BlockPos origin = context.getOrigin();
        StructureWorldAccess world = context.getWorld();
        Random rand = world.getRandom();


        BlockState groundState = world.getBlockState(origin.down());

        boolean succ = checkBoxState(origin, world, 2) && checkGroundState(groundState,origin.down(),world);

        if (!succ && config.bruteForceY()) {
            BlockPos.Mutable m;
            int movDir = INT_DIR.get(rand.nextBetween(0, 1));

            for (int a = 0; a < 2; a++) {
                m = new BlockPos.Mutable(origin.getX(), origin.getY(), origin.getZ());
                movDir = -movDir;

                for (int tries = 0; tries < MAX_RANGE; tries++) {
                    m.move(0, movDir, 0);
                    BlockState targState = world.getBlockState(m);
                    BlockState bottomState = world.getBlockState(m.down());
                    if (checkState(targState) && checkGroundState(bottomState,m.down(),world)) {
                        if (checkBoxState(m, world, 1)) {
                            succ = true;
                            origin = m.toImmutable();
                            break;
                        }
                    }else if (targState.isOf(Blocks.BEDROCK)) {
                        break;
                    }
                }

                if (succ) {
                    break;
                }
            }
        }

        if (succ) {
            world.setBlockState(origin, MPLBlocks.BINDING_ROCK.getDefaultState().with(BindingRockBlock.ROCK_SKIN,config.skin()),2);

            BlockPos.Mutable mutable = new BlockPos.Mutable(origin.getX(), origin.getY(), origin.getZ());

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
