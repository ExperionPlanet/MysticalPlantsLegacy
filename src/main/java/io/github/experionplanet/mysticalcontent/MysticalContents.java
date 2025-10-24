package io.github.experionplanet.mysticalcontent;


import io.github.experionplanet.init.MPLParticles;
import io.github.experionplanet.mysticalcontent.contents.SporeContent;
import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;

public class MysticalContents {
    public static final MysticIdMapping<SporeContent> EFFECT_SPORES = new MysticIdMapping<>();

    public static void bootstrap() {
        // Effect Spores
        EFFECT_SPORES.register(ExperionUtils.newId("bog"), new SporeContent(MPLParticles.BOG_SPORE, StatusEffects.POISON.getIdAsString()));


    }

    public static void build() {
        EFFECT_SPORES.build();
    }

}
