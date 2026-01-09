package io.github.experionplanet.mysticalplantslg.entitymodel;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class RootedModel extends Model {
    private final ModelPart root;

    public RootedModel(ModelPart root) {
        super(RenderLayer::getEntitySolid);
        this.root = root;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        this.root.render(matrices, vertices, light, overlay);
    }
}
