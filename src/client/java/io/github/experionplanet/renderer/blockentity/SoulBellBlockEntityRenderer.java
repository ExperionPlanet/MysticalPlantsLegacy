package io.github.experionplanet.renderer.blockentity;

import io.github.experionplanet.blocks.custom.SoulBellBlock;
import io.github.experionplanet.blocks.entity.LastTickedBlockEntity;
import io.github.experionplanet.blocks.entity.custom.SoulBellBlockEntity;
import io.github.experionplanet.utils.EasingsList;
import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class SoulBellBlockEntityRenderer extends BouncingPlantBlockEntityRenderer {
    private static final List<Float> STG_SIZE = List.of(1f,1f, 1.5f, 2f);

    public SoulBellBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(LastTickedBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        float totalSize = 1f;
        World world = blockEntity.getWorld();
        BlockPos pos = blockEntity.getPos();
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof SoulBellBlock) {
            int currStage = state.get(SoulBellBlock.ROUND);
            int prevStage = currStage - 1;

            if (prevStage > 0) {
                float alpha = 1f;
                if (blockEntity.getTicked(SoulBellBlockEntity.T_SOUL) != LastTickedBlockEntity.NULL_CLOCK) {
                    alpha = getAlpha(world.getTime(), blockEntity.getTicked(SoulBellBlockEntity.T_SOUL), tickDelta, 20f);
                }
                totalSize = ExperionUtils.tweenHandling(STG_SIZE.get(prevStage), STG_SIZE.get(currStage), (float) EasingsList.OutBack( alpha));
            }

            bounceRender(blockEntity, tickDelta, matrices, vertexConsumers, light, overlay, totalSize);
        }

    }
}
