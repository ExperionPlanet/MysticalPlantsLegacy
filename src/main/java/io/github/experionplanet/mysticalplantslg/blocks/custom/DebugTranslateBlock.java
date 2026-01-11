package io.github.experionplanet.mysticalplantslg.blocks.custom;

import com.mojang.serialization.MapCodec;
import io.github.experionplanet.mysticalplantslg.blocks.entity.custom.DebugTranslateBlockEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class DebugTranslateBlock extends BlockWithEntity {

    public DebugTranslateBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(DebugTranslateBlock::new);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DebugTranslateBlockEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient()) {
            DebugTranslateBlockEntity blockEntity = (DebugTranslateBlockEntity) world.getBlockEntity(pos);

            ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);
            if (stack.isOf(Items.REDSTONE)) {
                blockEntity.setValues(1);
                return ActionResult.SUCCESS;
            }

            if (stack.isOf(Items.GLOWSTONE_DUST)) {
                blockEntity.setValues(2);
                return ActionResult.SUCCESS;
            }

            if (stack.isOf(Items.BLAZE_POWDER)) {
                blockEntity.setValues(3);
                return ActionResult.SUCCESS;
            }

            if (stack.isOf(Items.GOLD_INGOT)) {
                blockEntity.setValues(4);
                return ActionResult.SUCCESS;
            }

            if (stack.isOf(Items.IRON_INGOT)) {
                blockEntity.setValues(5);
                return ActionResult.SUCCESS;
            }

            if (stack.isOf(Items.DIAMOND)) {
                blockEntity.setValues(6);
                return ActionResult.SUCCESS;
            }

            if (stack.isOf(Items.STICK)) {
                if (blockEntity.modeAddings == 1) {
                    blockEntity.setMode(2);
                }else {
                    blockEntity.setMode(1);
                }

                return ActionResult.SUCCESS;
            }


            stack = player.getStackInHand(Hand.OFF_HAND);

            if (!stack.isEmpty()) {

                blockEntity.setStack(stack);
            }
        }

        return ActionResult.PASS;
    }
}
