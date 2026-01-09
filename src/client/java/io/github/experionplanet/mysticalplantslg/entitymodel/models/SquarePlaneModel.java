package io.github.experionplanet.mysticalplantslg.entitymodel.models;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class SquarePlaneModel extends Model {
    protected final ModelPart root;

    public SquarePlaneModel(ModelPart root) {
        super(RenderLayer::getEntityCutout);

        this.root = root;
    }

    public static TexturedModelData getTexturedModelData(int size) {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        float pivotNum = size/2f;

        modelPartData.addChild("main", ModelPartBuilder.create().uv(0,0).cuboid(-size, 0f, -size, size, 0f, size), ModelTransform.pivot(pivotNum, 0, pivotNum));


        return TexturedModelData.of(modelData, size, size);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        this.root.render(matrices,vertices,light,overlay);
    }
}
