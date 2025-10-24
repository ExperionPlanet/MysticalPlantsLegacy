package io.github.experionplanet.renderer.blockentity;

import io.github.experionplanet.blocks.entity.custom.PermafrostLogBlockEntity;
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

public class PermafrostLogBlockEntityRenderer implements BlockEntityRenderer<PermafrostLogBlockEntity> {
    private final BlockRenderManager rendManager;

    public PermafrostLogBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.rendManager = context.getRenderManager();
    }

    @Override
    public void render(PermafrostLogBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        World world = entity.getWorld();

        BlockState cacheState = entity.getCachedState();
        BakedModel model = this.rendManager.getModel(cacheState);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getCutout());
        rendManager.getModelRenderer().render(world,model,cacheState,entity.getPos(),matrices,vertexConsumer, false, Random.create(entity.getPos().asLong()), cacheState.getRenderingSeed(entity.getPos()), overlay);
        matrices.pop();
    }
}
