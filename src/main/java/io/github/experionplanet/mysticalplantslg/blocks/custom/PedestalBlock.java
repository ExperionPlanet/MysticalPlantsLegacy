package io.github.experionplanet.mysticalplantslg.blocks.custom;

import com.mojang.serialization.MapCodec;
import io.github.experionplanet.mysticalplantslg.blocks.entity.custom.PedestalBlockEntity;
import io.github.experionplanet.mysticalplantslg.utils.ExperionLogger;
import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import static io.github.experionplanet.mysticalplantslg.init.MPLBlockProperties.ON_CRAFTING;

public class PedestalBlock extends BlockWithEntity {
    private static final VoxelShape SHAPE = Block.createCuboidShape(1, 0, 1, 15, 12, 15);

    public PedestalBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(ON_CRAFTING, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(ON_CRAFTING);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(PedestalBlock::new);
    }


    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PedestalBlockEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient() && !state.get(ON_CRAFTING)) {

            if (world.getBlockEntity(pos) instanceof PedestalBlockEntity) {
                PedestalBlockEntity blockEntity = (PedestalBlockEntity) world.getBlockEntity(pos);

                boolean succ = false;

                if (!blockEntity.getCurrentStack().isEmpty()) {
                    Vec3d vec = MysticalUtils.v3dConvert(pos, true);
                    ItemStack copiedStack = blockEntity.getCurrentStack().copy();
                    world.spawnEntity(new ItemEntity(world, vec.x, vec.y, vec.z, copiedStack));
                    if (stack.isEmpty()) {
                        blockEntity.emptyStack();
                    }

                    succ = true;
                }

                if (!stack.isEmpty()) {
                    blockEntity.setStack(stack.copyWithCount(1));
                    stack.decrementUnlessCreative(1, player);
                    succ = true;
                }

                if (succ) {
                    return ItemActionResult.SUCCESS;
                }
            }
        }

        return ItemActionResult.SUCCESS;
    }


    @Override
    protected float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        if (state.get(ON_CRAFTING)) {
            return 0f;
        }

        return super.calcBlockBreakingDelta(state, player, world, pos);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        if (!world.isClient()) {
            if (world.getBlockEntity(pos) instanceof PedestalBlockEntity blockEntity) {
                if (!blockEntity.getCurrentStack().isEmpty()) {
                    Vec3d v = MysticalUtils.v3dConvert(pos, true);
                    world.spawnEntity(new ItemEntity((World) world,  v.getX(), v.getY(), v.getZ(), blockEntity.getCurrentStack().copy()));
                }
            }
        }

        super.onBroken(world, pos, state);

    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            if (world.getBlockEntity(pos) instanceof PedestalBlockEntity blockEntity) {
                if (!blockEntity.getCurrentStack().isEmpty()) {
                    ItemScatterer.spawn(world, pos, blockEntity.storedItems());
                }

            }
        }

        super.onStateReplaced(state, world, pos, newState, moved);
    }
}
