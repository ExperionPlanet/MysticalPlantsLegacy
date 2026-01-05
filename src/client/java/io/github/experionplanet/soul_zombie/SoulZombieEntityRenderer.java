package io.github.experionplanet.soul_zombie;

import io.github.experionplanet.utils.MysticalUtils;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;

public class SoulZombieEntityRenderer extends ZombieEntityRenderer {
    public static final Identifier TEXTURE = MysticalUtils.newId("textures/entity/soul_zombie.png");

    public SoulZombieEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(ZombieEntity zombieEntity) {
        return TEXTURE;
    }

}
