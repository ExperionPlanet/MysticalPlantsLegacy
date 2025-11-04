package io.github.experionplanet.soul_zombie;

import io.github.experionplanet.entities.SoulZombieEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.ZombieEntityModel;
import net.minecraft.entity.mob.ZombieEntity;

public class SoulZombieEntityModel<S extends SoulZombieEntity> extends ZombieEntityModel<SoulZombieEntity> {
    public SoulZombieEntityModel(ModelPart modelPart) {
        super(modelPart);
    }
}
