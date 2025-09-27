package io.github.experionplanet.renderer.blockentity;

import io.github.experionplanet.blocks.entity.ExpMushroomBlockEntity;
import io.github.experionplanet.utils.EasingsList;
import io.github.experionplanet.utils.ExperionLogger;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.BlockStateModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
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
    public void render(ExpMushroomBlockEntity entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
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
        BlockStateModel model = this.rendManager.getModel(cacheState);
        this.rendManager.getModelRenderer().render(entity.getWorld(), model, cacheState, entity.getPos(), matrices, vertexConsumers, false, cacheState.getRenderingSeed(entity.getPos()), overlay);

        matrices.pop();

    }

    @Override
    public boolean rendersOutsideBoundingBox(ExpMushroomBlockEntity blockEntity) {
        return true;
    }
}
