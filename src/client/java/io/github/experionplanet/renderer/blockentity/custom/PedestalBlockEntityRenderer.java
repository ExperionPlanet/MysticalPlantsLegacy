package io.github.experionplanet.renderer.blockentity.custom;

import io.github.experionplanet.blocks.entity.custom.PedestalBlockEntity;
import io.github.experionplanet.utils.MysticalUtils;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;

public class PedestalBlockEntityRenderer implements BlockEntityRenderer<PedestalBlockEntity> {
    private final ItemRenderer itemRenderer;

    public PedestalBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }
    @Override
    public void render(PedestalBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemStack stack = entity.getCurrentStack();
        if (!stack.isEmpty() && entity.getWorld() != null) {

            matrices.push();

            Random selfRand = Random.create(entity.getPos().asLong());

            float offsetAnim = MysticalUtils.floatInRange(selfRand, 0, 199);
            float clock = ((float) entity.getWorld().getTime()) + tickDelta + offsetAnim;

            matrices.translate(0.5,1.05 + (float) Math.sin(clock * 0.15f) * 0.025,0.5);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(clock));
            matrices.scale(1f, 1f, 1f);

            itemRenderer.renderItem(stack, ModelTransformationMode.GROUND, light, overlay, matrices, vertexConsumers, entity.getWorld(), (int) entity.getPos().asLong());

            matrices.pop();
        }
    }
}
