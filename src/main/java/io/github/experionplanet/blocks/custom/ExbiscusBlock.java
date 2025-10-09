package io.github.experionplanet.blocks.custom;

import com.mojang.serialization.MapCodec;
import io.github.experionplanet.blocks.MysticalPlantBlockWithEntity;
import io.github.experionplanet.blocks.entity.custom.ExbiscusBlockEntity;
import io.github.experionplanet.init.MPLBlockProperties;
import io.github.experionplanet.init.MPLItems;
import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ExbiscusBlock extends MysticalPlantBlockWithEntity {
    public static final BooleanProperty BLOOMING = MPLBlockProperties.BLOOMING;

    public ExbiscusBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(BLOOMING, false));
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(BLOOMING);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(ExbiscusBlock::new);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ExbiscusBlockEntity(pos,state);
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return !state.get(BLOOMING);
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int resChance = random.nextBetween(1, 10);

        if (resChance <= 1) {
            world.setBlockState(pos, state.with(BLOOMING, true));
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (state.get(BLOOMING)) {
            if (!world.isClient()) {
                world.setBlockState(pos, state.with(BLOOMING, false));

                Random rand =  world.getRandom();
                Vec3d vec = ExperionUtils.v3dConvert(pos, true);
                if (rand.nextBetween(1, 15) <= 1) {

                    world.spawnEntity(new ItemEntity(world, vec.getX(), vec.y, vec.z, new ItemStack(MPLItems.EXPERIENCE_ESSENCE, 1)));
                }else {
                    ExperienceOrbEntity.spawn((ServerWorld) world, vec, rand.nextBetween(2, 5));
                }
            }

            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}
