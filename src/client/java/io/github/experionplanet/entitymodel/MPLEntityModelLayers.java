package io.github.experionplanet.entitymodel;

import io.github.experionplanet.entitymodel.models.ExpRingModel;
import io.github.experionplanet.utils.ExperionUtils;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class MPLEntityModelLayers {
    public static final EntityModelLayer SMALL_EXP_RING = register("small_exp_ring");

    private static EntityModelLayer register(String str) {

        return new EntityModelLayer(ExperionUtils.newId(str), "main");
    }


    public static void init() {
        EntityModelLayerRegistry.registerModelLayer(
                SMALL_EXP_RING,
                ExpRingModel::getTexturedModelData
        );
    }


}
