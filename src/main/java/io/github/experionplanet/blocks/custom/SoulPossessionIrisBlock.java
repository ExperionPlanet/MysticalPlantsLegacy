package io.github.experionplanet.blocks.custom;

import com.mojang.serialization.MapCodec;
import io.github.experionplanet.blocks.MysticalPlantBlockWithEntity;
import io.github.experionplanet.blocks.entity.ContainerBlockEntity;
import io.github.experionplanet.blocks.entity.SoulPossessionIrisBlockEntity;
import io.github.experionplanet.init.MPLItems;
import io.github.experionplanet.utils.MysticalUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import static io.github.experionplanet.init.MPLBlockProperties.BLOOMING;

public class SoulPossessionIrisBlock extends MysticalPlantBlockWithEntity {
    public static final BooleanProperty HAS_SOUL = BooleanProperty.of("has_soul");

    public SoulPossessionIrisBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(HAS_SOUL, false).with(BLOOMING, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HAS_SOUL, BLOOMING);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SoulPossessionIrisBlockEntity(pos, state);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(SoulPossessionIrisBlock::new);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    private static void emptyContainer(ContainerBlockEntity blockEntity, World world, BlockPos pos) {
        if (!blockEntity.getCurrentStack().isEmpty()) {
            Vec3d vec = MysticalUtils.v3dConvert(pos, true);
            ItemEntity itemEntity = new ItemEntity(world, vec.x, vec.y, vec.z, blockEntity.getCurrentStack().copyWithCount(1));
            world.spawnEntity(itemEntity);
            blockEntity.emptyStack();
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);

        if (world.getBlockEntity(pos) instanceof ContainerBlockEntity blockEntity) {
            if (!stack.isEmpty()) {
                if (stack.isOf(MPLItems.SOUL)) {
                    if (!world.isClient()) {
                        emptyContainer(blockEntity, world, pos);

                        blockEntity.setStack(stack.copyWithCount(1));
                        stack.decrement(1);
                        world.setBlockState(pos,state.with(HAS_SOUL, true));
                    }
                    return ActionResult.SUCCESS;
                }
            }else {
                if (!world.isClient()) {
                    emptyContainer(blockEntity, world, pos);
                    world.setBlockState(pos,state.with(HAS_SOUL, false).with(BLOOMING, false));
                }
                return ActionResult.SUCCESS;
            }
        }


        return ActionResult.PASS;
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return state.get(HAS_SOUL) && !state.get(BLOOMING);
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int resChance = random.nextBetween(1, 10);

        if (resChance <= 1) {
            if (world.getBlockEntity(pos) instanceof ContainerBlockEntity blockEntity) {
                world.setBlockState(pos, state.with(BLOOMING, true));
                if (random.nextFloat() <= 0.1) {
                    blockEntity.setStack(new ItemStack(MPLItems.SOUL_ESSENCE));
                }else {
                    blockEntity.setStack(new ItemStack(MPLItems.SOUL_POLLEN));
                }

            }
        }
    }
}
