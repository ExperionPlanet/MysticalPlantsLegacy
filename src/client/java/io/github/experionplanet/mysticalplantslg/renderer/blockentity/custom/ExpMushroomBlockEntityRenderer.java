package io.github.experionplanet.mysticalplantslg.renderer.blockentity.custom;

import io.github.experionplanet.mysticalplantslg.blocks.entity.custom.ExpMushroomBlockEntity;
import io.github.experionplanet.mysticalplantslg.utils.EasingsList;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class ExpMushroomBlockEntityRenderer implements BlockEntityRenderer<ExpMushroomBlockEntity> {
    private static final float SQUISHED_SCALE = 0.5f;
    private static final float SQUISHING_IN = 5f;
    private static final float SQUISHING_OUT = 10f;
    private final BlockRenderManager rendManager;
    public ExpMushroomBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
       this.rendManager = context.getRenderManager();
    }

    @Override
    public void render(ExpMushroomBlockEntity entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();

        World world = entity.getWorld();

        float TotalScale = 1f;
        float TotalAlpha = 1f;
        float progress = world.getTime() + tickProgress;
        float lastTick = entity.LAST_TICK;

        int StepStatus = entity.STEP_STATUS;

        if (lastTick > 0) {
            if (StepStatus == 1) { // On Stepped
                TotalAlpha = Math.min((progress - lastTick) / SQUISHING_IN, 1);
                TotalScale = 1 + (SQUISHED_SCALE - 1) * ((float) EasingsList.OutExponent2(TotalAlpha));
            } else if (StepStatus == 2) { // On Leave
                TotalAlpha = Math.min((progress - lastTick) / SQUISHING_OUT, 1);
                TotalScale = SQUISHED_SCALE + (1 - SQUISHED_SCALE) * ((float) EasingsList.OutElastic(TotalAlpha));
            }
        }


        matrices.scale(1f, TotalScale, 1f);



        BlockState cacheState = entity.getCachedState();
        BakedModel model = this.rendManager.getModel(cacheState);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getCutout());
        rendManager.getModelRenderer().render(world,model,cacheState,entity.getPos(),matrices,vertexConsumer, false, Random.create(entity.getPos().asLong()), cacheState.getRenderingSeed(entity.getPos()), overlay);
        matrices.pop();

    }

    @Override
    public boolean rendersOutsideBoundingBox(ExpMushroomBlockEntity blockEntity) {
        return true;
    }
}
