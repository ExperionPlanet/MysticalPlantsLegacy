package io.github.experionplanet.renderer.blockentity;

import io.github.experionplanet.blocks.entity.custom.DebugTranslateBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;

public class DebugTranslateBlockEntityRenderer implements BlockEntityRenderer<DebugTranslateBlockEntity> {
    private final ItemRenderer itemRenderer;

    public DebugTranslateBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(DebugTranslateBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemStack stack = entity.getCurrentStack();
        BlockState state = entity.getCachedState();

        if (!stack.isEmpty()) {
            matrices.push();

            matrices.translate(entity.tx, entity.ty, entity.tz);


            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(entity.rx));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.ry));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(entity.rz));

            this.itemRenderer.renderItem(entity.getCurrentStack(), ModelTransformationMode.FIXED, light, overlay, matrices, vertexConsumers, entity.getWorld(), (int) entity.getPos().asLong());

            matrices.pop();
        }
    }
}
