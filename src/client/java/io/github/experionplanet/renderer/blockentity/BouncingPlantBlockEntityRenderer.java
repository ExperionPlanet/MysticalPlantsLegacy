package io.github.experionplanet.renderer.blockentity;

import io.github.experionplanet.blocks.entity.LastTickedBlockEntity;
import io.github.experionplanet.blocks.entity.custom.BouncingPlantBlockEntity;
import io.github.experionplanet.utils.EasingsList;
import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class BouncingPlantBlockEntityRenderer implements BlockEntityRenderer<LastTickedBlockEntity> {
    private static final float DURATION_OUT = 30f;
    private static final float SQUISH_IN = 0.2f;

    private final BlockRenderManager rendManager;

    public BouncingPlantBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.rendManager = context.getRenderManager();

    }

    @Override
    public void render(LastTickedBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
       bounceRender(entity, tickDelta, matrices, vertexConsumers, light, overlay, 1f);
    }

    protected static float getAlpha(long currentTime, long lastClock, float tickDelta, float duration) {
        return ExperionUtils.alphaHandling(((((float) currentTime) + tickDelta) - (float) lastClock), duration);
    }

    protected void bounceRender(LastTickedBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, float size) {
        World world = entity.getWorld();

        long lastClock = entity.getTicked(BouncingPlantBlockEntity.T_STEPPED);

        float alpha = 1f;

        if (lastClock != LastTickedBlockEntity.NULL_CLOCK) {
            alpha = getAlpha(world.getTime(), lastClock, tickDelta, DURATION_OUT); //ExperionUtils.alphaHandling(((((float) world.getTime()) + tickDelta) - (float) lastClock), DURATION_OUT);
        }

        matrices.push();

        float totalScale = ExperionUtils.tweenHandling(SQUISH_IN, size, (float) EasingsList.OutElastic(alpha));
        matrices.translate(0.5, 0, 0.5);
        matrices.scale(size,totalScale,size);
        matrices.translate(-0.5, 0, -0.5);

        BlockState cacheState = entity.getCachedState();
        BakedModel model = this.rendManager.getModel(cacheState);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getCutout());
        rendManager.getModelRenderer().render(world,model,cacheState,entity.getPos(),matrices,vertexConsumer, false, Random.create(entity.getPos().asLong()), cacheState.getRenderingSeed(entity.getPos()), overlay);

        matrices.pop();
    }
}
