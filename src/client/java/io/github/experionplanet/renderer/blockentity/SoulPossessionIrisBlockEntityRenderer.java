package io.github.experionplanet.renderer.blockentity;

import io.github.experionplanet.blocks.entity.ContainerBlockEntity;
import io.github.experionplanet.entitymodel.models.SquarePlaneModel;
import io.github.experionplanet.init.MPLBlockProperties;
import io.github.experionplanet.init.MPLEntityModelLayers;
import io.github.experionplanet.utils.ExperionLogger;
import io.github.experionplanet.utils.MysticalUtils;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;

public class SoulPossessionIrisBlockEntityRenderer implements BlockEntityRenderer<ContainerBlockEntity> {
    private final ItemRenderer itemRenderer;
    private final SquarePlaneModel ring;
    private static final int totalFrames = 7;
    private static final Identifier[] FrameTexture = new Identifier[totalFrames];

    static {
        for (int i = 0; i < totalFrames; i++) {
            FrameTexture[i] = MysticalUtils.newId("textures/block/soul_ring/soul_ring_" + (i + 1) + ".png");
        }
    }

    public SoulPossessionIrisBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.itemRenderer = context.getItemRenderer();
        this.ring = new SquarePlaneModel(context.getLayerModelPart(MPLEntityModelLayers.FULL_PLANE));

        for (Identifier w : FrameTexture) {
            ExperionLogger.Print(w.toString());
        }
    }

    @Override
    public void render(ContainerBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        BlockState state = entity.getCachedState();
        ItemStack stack = entity.getCurrentStack();

        if (!stack.isEmpty()) {
            // Item
            matrices.push();

            Random selfRand = Random.create(entity.getPos().asLong());

            float offsetAnim = MysticalUtils.floatInRange(selfRand, 0, 199);
            float clock = ((float) entity.getWorld().getTime()) + tickDelta + offsetAnim;

            float sRot = 0.2f; // Speed
            float fRot = 2.5f;
            float xRot = (float) Math.sin(clock * sRot) * fRot;
            float yRot = (float) Math.cos(clock * sRot) * fRot;

            matrices.translate(0.5d,0.85 + (float) Math.sin(clock * 0.15f) * 0.025,0.5d);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90 + xRot));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(clock));
            matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(0 + yRot));
            matrices.scale(0.6f, 0.6f, 0.6f);

            itemRenderer.renderItem(stack, ModelTransformationMode.FIXED, light, overlay, matrices, vertexConsumers, entity.getWorld(), (int) entity.getPos().asLong());

            matrices.pop();

            // Blooming Ring
            if (state.get(MPLBlockProperties.BLOOMING)) {
                matrices.push();

                matrices.translate(0.5, 0.5, 0.5);
                matrices.scale(1.0F, -1.0F, -1.0F);

                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90 * selfRand.nextBetween(1, 4)));

                int frameIndex = (int)(clock / 3) % totalFrames;
                Identifier currFrame = FrameTexture[frameIndex];

                VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(currFrame));
                this.ring.render(matrices, vertexConsumer, light, overlay);

                matrices.pop();
            }

        }
    }
}
