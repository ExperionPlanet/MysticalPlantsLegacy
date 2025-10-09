package io.github.experionplanet.blocks.custom;

import com.mojang.serialization.MapCodec;
import io.github.experionplanet.blocks.entity.custom.PedestalBlockEntity;
import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PedestalBlock extends BlockWithEntity {
    private static final VoxelShape SHAPE = Block.createCuboidShape(1, 0, 1, 15, 12, 15);
    private static final BooleanProperty ON_CRAFTING = BooleanProperty.of("on_crafting");

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
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient() && !state.get(ON_CRAFTING)) {
            ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);

            if (stack.isEmpty()) {
                stack = player.getStackInHand(Hand.OFF_HAND);
            }

            //ExperionLogger.Print(stack.toString());

            if (world.getBlockEntity(pos) instanceof PedestalBlockEntity) {
                PedestalBlockEntity blockEntity = (PedestalBlockEntity) world.getBlockEntity(pos);

                boolean succ = false;

                if (!blockEntity.getCurrentStack().isEmpty()) {
                    Vec3d vec = ExperionUtils.v3dConvert(pos, true);
                    ItemStack copiedStack = blockEntity.getCurrentStack().copy();

                    world.spawnEntity(new ItemEntity(world, vec.x, vec.y, vec.z, copiedStack));
                    if (stack.isEmpty()) {
                        blockEntity.emptyStack();
                    }
                    succ = true;

                }

                if (!stack.isEmpty()) {
                    blockEntity.setStack(stack.copyWithCount(1));
                    stack.decrement(1);
                    succ = true;
                }

                if (succ) {
                    return ActionResult.SUCCESS;
                }
            }
        }

        return ActionResult.PASS;
    }

    @Override
    protected float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        if (state.get(ON_CRAFTING)) {
            return 0f;
        }

        return super.calcBlockBreakingDelta(state, player, world, pos);
    }
}
