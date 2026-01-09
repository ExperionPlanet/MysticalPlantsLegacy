package io.github.experionplanet.mysticalplantslg.entitymodel.models;

import io.github.experionplanet.mysticalplantslg.entitymodel.RootedModel;
import net.minecraft.client.model.*;

public class CubeOrbModel extends RootedModel {
    public CubeOrbModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData bone = modelPartData.addChild("bone", ModelPartBuilder.create().uv(0, 0).cuboid(-11.0F, -6.0F, 5.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(8.0F, 24.0F, -8.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }
}
