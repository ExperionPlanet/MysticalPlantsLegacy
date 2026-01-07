package io.github.experionplanet.renderer.blockentity;

import io.github.experionplanet.init.MPLBlockProperties;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class SnowableBlockEntityRenderer {
    public static void render(BlockEntity blockEntity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BlockRenderManager rend) {
        BlockState state = blockEntity.getCachedState();
        if (state.contains(MPLBlockProperties.SNOW)) {
            if (state.get(MPLBlockProperties.SNOW)) {
                World world = blockEntity.getWorld();
                BlockState snowState = Blocks.SNOW.getDefaultState();
                BakedModel model = rend.getModel(snowState);
                VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getSolid());
                rend.getModelRenderer().render(world,model,snowState,blockEntity.getPos(),matrices,vertexConsumer, false, Random.create(blockEntity.getPos().asLong()), snowState.getRenderingSeed(blockEntity.getPos()), overlay);
            }
        }
    }
}
