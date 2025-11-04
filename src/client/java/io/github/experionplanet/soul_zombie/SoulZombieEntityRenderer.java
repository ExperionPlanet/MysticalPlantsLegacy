package io.github.experionplanet.soul_zombie;

import io.github.experionplanet.entities.SoulZombieEntity;
import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieBaseEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.ZombieEntityModel;
import net.minecraft.util.Identifier;

public class SoulZombieEntityRenderer extends ZombieBaseEntityRenderer<SoulZombieEntity, SoulZombieEntityModel<SoulZombieEntity>> {
    public static final Identifier TEXTURE = ExperionUtils.newId("textures/entity/soul_zombie.png");
    public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(ExperionUtils.newId("soul_zombie"), "main");

    public SoulZombieEntityRenderer(EntityRendererFactory.Context ctx, SoulZombieEntityModel<SoulZombieEntity> bodyModel, SoulZombieEntityModel<SoulZombieEntity> legsArmorModel, SoulZombieEntityModel<SoulZombieEntity> bodyArmorModel) {
        super(ctx, bodyModel, legsArmorModel, bodyArmorModel);
    }

    public SoulZombieEntityRenderer(EntityRendererFactory.Context ctx, EntityModelLayer layer, EntityModelLayer legsArmorLayer, EntityModelLayer bodyArmorLayer) {
        super(ctx, new SoulZombieEntityModel<>(ctx.getPart(layer)), new SoulZombieEntityModel<>(ctx.getPart(legsArmorLayer)), new SoulZombieEntityModel<>(ctx.getPart(bodyArmorLayer)));
    }

    @Override
    public Identifier getTexture(SoulZombieEntity entity) {
        return TEXTURE;
    }
}
