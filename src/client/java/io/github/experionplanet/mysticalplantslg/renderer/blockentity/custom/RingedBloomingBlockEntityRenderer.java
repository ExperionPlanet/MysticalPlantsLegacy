package io.github.experionplanet.mysticalplantslg.renderer.blockentity.custom;

import io.github.experionplanet.mysticalplantslg.compat.MPLConfig;
import io.github.experionplanet.mysticalplantslg.init.MPLEntityModelLayers;
import io.github.experionplanet.mysticalplantslg.blocks.entity.custom.BloomingFlowerBlockEntity;
import io.github.experionplanet.mysticalplantslg.entitymodel.models.SquarePlaneModel;
import io.github.experionplanet.mysticalplantslg.init.MPLBlockProperties;
import io.github.experionplanet.mysticalplantslg.mysticalcontents.MysticalContentsClient;
import io.github.experionplanet.mysticalplantslg.mysticalcontents.content.RingedBloomingContent;
import io.github.experionplanet.mysticalplantslg.renderer.blockentity.SnowableBlockEntityRenderer;
import io.github.experionplanet.mysticalplantslg.utils.ExperionLogger;
import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;

public class RingedBloomingBlockEntityRenderer implements BlockEntityRenderer<BloomingFlowerBlockEntity> {
    private final BlockRenderManager rendManager;
    private final SquarePlaneModel ring;

    public RingedBloomingBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.rendManager = context.getRenderManager();
        this.ring = new SquarePlaneModel(context.getLayerModelPart(MPLEntityModelLayers.FULL_PLANE));

    }



    @Override
    public void render(BloomingFlowerBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        SnowableBlockEntityRenderer.render(entity, matrices, vertexConsumers, light, overlay, rendManager);

        if (entity.getCachedState().get(MPLBlockProperties.BLOOMING)) {
            if (MPLConfig.show_flower_rings) {
                RingedBloomingContent content;
                if (entity.CONTENT_TYPE.equals("null")) {
                    entity.CONTENT_TYPE = Registries.BLOCK.getId(entity.getCachedState().getBlock()).getPath();
                }
                String key = entity.CONTENT_TYPE;
                if (MysticalContentsClient.RINGED_BLOOMING_CONTENT.containsKey(key)) {
                    content = MysticalContentsClient.RINGED_BLOOMING_CONTENT.get(key);
                }else {
                    ExperionLogger.Warn("CONTENT MISSING! " + key);
                    return;
                }

                double offsetX = content.x;
                double offsetY = content.y;
                double offsetZ = content.z;

                matrices.push();
                double FloatNum = 0;
                float angle = 0;

                if (MPLConfig.animated_rings) {
                    Random selfRand = Random.create(entity.getPos().asLong());

                    float offsetAnim = MysticalUtils.floatInRange(selfRand, 0, 199);
                    float myClock = ((float) entity.getWorld().getTime()) + offsetAnim;

                    if (!content.lowFPS) {
                        myClock += tickDelta;
                    }

                    angle = (myClock * content.speed) % 360;

                    if (content.floatAnim) {
                        FloatNum = ((float) Math.sin(myClock * 0.15f) * 0.025);
                    }

                }

                matrices.translate(offsetX, offsetY + FloatNum, offsetZ);
                matrices.scale(1.0F, -1.0F, -1.0F);

                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(angle));
                VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(MysticalUtils.newId(content.texture)));
                this.ring.render(matrices, vertexConsumer, light, overlay);

                matrices.pop();
            }

        }

    }



}
