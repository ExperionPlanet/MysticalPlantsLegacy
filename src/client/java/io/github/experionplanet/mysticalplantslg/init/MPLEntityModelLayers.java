package io.github.experionplanet.mysticalplantslg.init;

import io.github.experionplanet.mysticalplantslg.entitymodel.models.CubeOrbModel;
import io.github.experionplanet.mysticalplantslg.entitymodel.models.SquarePlaneModel;
import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class MPLEntityModelLayers {
    public static final EntityModelLayer FULL_PLANE = register("full_plane");
    public static final EntityModelLayer PLANE_13 = register("plane_13");
    public static final EntityModelLayer RING_ORB = register("ring_orb");

    private static EntityModelLayer register(String str) {

        return new EntityModelLayer(MysticalUtils.newId(str), "main");
    }


    public static void init() {
        EntityModelLayerRegistry.registerModelLayer(FULL_PLANE, () -> SquarePlaneModel.getTexturedModelData(16));
        EntityModelLayerRegistry.registerModelLayer(PLANE_13, () -> SquarePlaneModel.getTexturedModelData(13));
        EntityModelLayerRegistry.registerModelLayer(RING_ORB, CubeOrbModel::getTexturedModelData);

    }


}
