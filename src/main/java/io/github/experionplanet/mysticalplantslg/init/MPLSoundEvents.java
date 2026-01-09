package io.github.experionplanet.mysticalplantslg.init;

import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class MPLSoundEvents {
    public static final SoundEvent EXBISCUS_BLOOMING_PICK = registerSound("exbiscus_blooming_pick");
    public static final SoundEvent EXBISCUS_BLOOMING_PICK_ESSENCE = registerSound("exbiscus_blooming_pick_essence");
    public static final SoundEvent PERMAFROST_SHROOM_EXPLODE = registerSound("permafrost_shroom_explode");
    public static final SoundEvent PERMAFROST_SHROOM_EXPLODE_LAST = registerSound("permafrost_shroom_explode_last");
    public static final SoundEvent FROST_UMBRELLA_FLOWER_BOINK = registerSound("frost_umbrella_flower_boink");
    public static final SoundEvent EXP_MUSHROOMS_BOUNCE = registerSound("exp_mushrooms_bounce");
    public static final SoundEvent EXPERIENCE_PICKAXE_BURST = registerSound("experience_pickaxe_burst");

    private static SoundEvent registerSound(String id) {
        Identifier identifier = MysticalUtils.newId(id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    public static void init() {}
}
