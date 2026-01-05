package io.github.experionplanet.blocks.custom;

import com.mojang.serialization.MapCodec;
import io.github.experionplanet.blocks.entity.custom.BindingRockBlockEntity;
import io.github.experionplanet.blocks.entity.custom.PedestalBlockEntity;
import io.github.experionplanet.init.MPLBlockEntities;
import io.github.experionplanet.utils.ExperionLogger;
import io.github.experionplanet.recipe.MysticalPedestalRecipe;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static io.github.experionplanet.init.MPLBlockProperties.ON_CRAFTING;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BindingRockBlock extends BlockWithEntity {
    private static final VoxelShape SHAPE = Block.createCuboidShape(0,0,0, 16, 14, 16);
    public static final BlockPos[] PEDESTAL_POS_LIST = {
            new BlockPos(0, 0, 3),
            new BlockPos(0, 0, -3),
            new BlockPos(-3, 0, 0),
            new BlockPos(3, 0, 0),
            new BlockPos(2, 0, 2),
            new BlockPos(2, 0, -2),
            new BlockPos(-2, 0, 2),
            new BlockPos(-2, 0, -2),
    };

    public BindingRockBlock(Settings settings) {
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
        return createCodec(BindingRockBlock::new);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BindingRockBlockEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient() && !state.get(ON_CRAFTING)) {
            if (world.getBlockEntity(pos) instanceof BindingRockBlockEntity) {
                BindingRockBlockEntity blockEntity = (BindingRockBlockEntity) world.getBlockEntity(pos);
                ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);
                if (stack.isEmpty()) {
                    stack = player.getStackInHand(Hand.OFF_HAND);
                }
                if (player.isCreative()) { // CREATIVE
                    ExperionLogger.Print(stack.toString());
                    if (!stack.isEmpty()) {
                        blockEntity.setStack(stack);
                        return ActionResult.SUCCESS;
                    }
                } else { // SURVIVAL
                    if (!stack.isEmpty()) {
                        int total = 0;

                        List<PedestalBlockEntity> pedestals = new ArrayList<>();
                        List<ItemStack> puttedStack = new ArrayList<>();

                        for (BlockPos v : PEDESTAL_POS_LIST) {
                            BlockPos targPos = pos.add(v.getX(), v.getY(),v.getZ());
                            if (world.getBlockEntity(targPos) instanceof PedestalBlockEntity) {
                                total += 1;
                                PedestalBlockEntity pedestal = (PedestalBlockEntity) world.getBlockEntity(targPos);
                                if (!pedestal.getCurrentStack().isEmpty() && !world.getBlockState(targPos).get(ON_CRAFTING)) {
                                    puttedStack.add(pedestal.getCurrentStack());
                                    pedestals.add(pedestal);
                                }
                            }
                        }

                        if (total < 8) {
                            for (int i = 1; i < 8 - total; i++) {
                                puttedStack.add(ItemStack.EMPTY);
                            }
                        }

                        Optional<RecipeEntry<MysticalPedestalRecipe>> recipe = blockEntity.getCurrentRecipe(puttedStack, stack);

                        if (!recipe.isEmpty()) {
                            ItemStack resultStack = recipe.get().value().getOutput();

                            for (PedestalBlockEntity pedestal : pedestals) {
                                world.setBlockState(pedestal.getPos(), world.getBlockState(pedestal.getPos()).with(ON_CRAFTING, true));
                            }

                            blockEntity.initializeCrafting(pedestals, puttedStack, stack.copy(), resultStack, world, pos);

                            stack.decrement(1);

                            world.setBlockState(pos, state.with(ON_CRAFTING, true));
                        }

                        return ActionResult.SUCCESS;
                    }


                }
            }


        }

        return ActionResult.PASS;
    }



    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (state.get(ON_CRAFTING)) {
            if (!world.isClient()) { // SERVER
                return validateTicker(type, MPLBlockEntities.BINDING_ROCK, BindingRockBlockEntity::onTickServer);
            }else { // CLIENT
                return validateTicker(type, MPLBlockEntities.BINDING_ROCK, BindingRockBlockEntity::onTickClient);
            }
        }

        return super.getTicker(world, state, type);
    }

    @Override
    protected float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof BindingRockBlockEntity blockEntity) {
            if (!blockEntity.getCurrentStack().isEmpty()) {
                return 0f;
            }
        }
        return super.calcBlockBreakingDelta(state, player, world, pos);
    }
}
