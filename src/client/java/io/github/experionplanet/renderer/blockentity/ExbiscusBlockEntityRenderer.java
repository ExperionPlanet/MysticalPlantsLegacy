package io.github.experionplanet.renderer.blockentity;

import io.github.experionplanet.entitymodel.MPLEntityModelLayers;
import io.github.experionplanet.blocks.entity.ExbiscusBlockEntity;
import io.github.experionplanet.entitymodel.models.ExpRingModel;
import io.github.experionplanet.init.MPLBlockProperties;
import io.github.experionplanet.utils.ExperionLogger;
import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;

public class ExbiscusBlockEntityRenderer implements BlockEntityRenderer<ExbiscusBlockEntity> {
    private final BlockRenderManager rendManager;
    private final ExpRingModel expRing;

    public ExbiscusBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.rendManager = context.getRenderManager();
        this.expRing = new ExpRingModel(context.getLayerModelPart(MPLEntityModelLayers.SMALL_EXP_RING));
    }



    @Override
    public void render(ExbiscusBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (entity.getCachedState().get(MPLBlockProperties.BLOOMING)) {
            matrices.push();

            Random selfRand = Random.create(entity.getPos().asLong());

            float offsetAnim = ExperionUtils.floatInRange(selfRand, 0, 199);

            float myClock = ((float) entity.getWorld().getTime()) + tickDelta + offsetAnim;

            matrices.translate(0.5, (1.0d - (2d/16d)) + ((float) Math.sin(myClock * 0.15f) * 0.025), 0.5);

            matrices.scale(1.0F, -1.0F, -1.0F);

            float angle = (myClock * 0.5f) % 360;
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(angle));

            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(ExperionUtils.newId("textures/block/small_exp_ring.png")));

            this.expRing.render(matrices, vertexConsumer, light, overlay);

            matrices.pop();
        }

    }



}
