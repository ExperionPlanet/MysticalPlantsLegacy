package io.github.experionplanet.blocks.custom;

import io.github.experionplanet.blocks.MysticalPlantBlock;
import io.github.experionplanet.init.MPLBiomeTags;
import io.github.experionplanet.init.MPLItems;
import io.github.experionplanet.items.tool.custom.SoulHoeItem;
import io.github.experionplanet.utils.MysticalUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import static io.github.experionplanet.init.MPLBlockProperties.BLOOMING;

public class SoulPitcherBlock extends MysticalPlantBlock {
    public SoulPitcherBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(BLOOMING, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(BLOOMING);
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return !state.get(BLOOMING);
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBiome(pos).isIn(MPLBiomeTags.SOUL)) {
            int resChance = random.nextBetween(1, 10);

            if (resChance <= 1) {
                world.setBlockState(pos, state.with(BLOOMING, true));
            }
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random rand) {
        if (state.get(BLOOMING)) {
            Vec3d v = MysticalUtils.v3dConvert(pos, true);
            double x = MysticalUtils.doubleInRange(rand, -0.5, 0.5);
            double y = MysticalUtils.doubleInRange(rand, -0.5, 0.5);
            double z = MysticalUtils.doubleInRange(rand, -0.5, 0.5);
            world.addParticle(ParticleTypes.SCULK_SOUL, v.getX() + x, v.getY() + y, v.getZ() + z, 0, 0, 0);
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (state.get(BLOOMING)) {
            if (!world.isClient()) {
                ItemStack handStack = player.getMainHandStack();
                if (handStack.isOf(MPLItems.SOUL_HOE)) {
                    SoulHoeItem.fillings(handStack, 1);
                }else {
                    Vec3d vec = MysticalUtils.v3dConvert(pos, true);
                    world.spawnEntity(new ItemEntity(world, vec.getX(), vec.getY(), vec.getZ(), new ItemStack(MPLItems.SOUL)));
                }
                world.setBlockState(pos, state.with(BLOOMING, false));
            }

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}
