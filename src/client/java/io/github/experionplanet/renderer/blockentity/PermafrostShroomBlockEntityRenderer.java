package io.github.experionplanet.renderer.blockentity;

import io.github.experionplanet.blocks.entity.LastTickedBlockEntity;
import io.github.experionplanet.blocks.entity.custom.PermafrostShroomBlockEntity;
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

public class PermafrostShroomBlockEntityRenderer implements BlockEntityRenderer<PermafrostShroomBlockEntity> {
    private static final float DURATION_OUT = 30f;
    private static final float SQUISH_IN = 0.2f;

    private final BlockRenderManager rendManager;

    public PermafrostShroomBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.rendManager = context.getRenderManager();
    }

    @Override
    public void render(PermafrostShroomBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = entity.getWorld();

        long lastClock = entity.getTicked(PermafrostShroomBlockEntity.T_STEPPED);
        float alpha = 1f;

        if (lastClock != LastTickedBlockEntity.NULL_CLOCK) {

            alpha = ExperionUtils.alphaHandling(((((float) world.getTime()) + tickDelta) - (float) lastClock), DURATION_OUT);
        }

        matrices.push();

        float totalScale = ExperionUtils.tweenHandling(SQUISH_IN, 1f, (float) EasingsList.OutElastic(alpha));

        matrices.scale(1,totalScale,1);

        BlockState cacheState = entity.getCachedState();
        BakedModel model = this.rendManager.getModel(cacheState);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getCutout());
        rendManager.getModelRenderer().render(world,model,cacheState,entity.getPos(),matrices,vertexConsumer, false, Random.create(entity.getPos().asLong()), cacheState.getRenderingSeed(entity.getPos()), overlay);

        matrices.pop();
    }
}
