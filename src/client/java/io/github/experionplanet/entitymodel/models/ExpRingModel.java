package io.github.experionplanet.entitymodel.models;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ExpRingModel extends Model {
    private final ModelPart root;

    public ExpRingModel(ModelPart root) {
        super(RenderLayer::getEntityCutout);

        this.root = root;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("main", ModelPartBuilder.create().uv(0,0).cuboid(-16.0f, 0f, -16.0f, 16f, 0f, 16f), ModelTransform.pivot(8f, 0, 8f));


        return TexturedModelData.of(modelData, 16, 16);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        this.root.render(matrices,vertices,light,overlay);
    }
}
