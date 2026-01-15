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
    public static final SoundEvent SOUL_BELL_STAGE_1 = registerSound("soul_bell_stage_1");
    public static final SoundEvent SOUL_BELL_STAGE_2 = registerSound("soul_bell_stage_2");
    public static final SoundEvent SOUL_BELL_STAGE_3 = registerSound("soul_bell_stage_3");
    public static final SoundEvent SOUL_POSSESION_IRIS_HARVEST = registerSound("soul_possesion_iris_harvest");
    public static final SoundEvent SOUL_PITCHER_HARVEST = registerSound("soul_pitcher_harvest");
    public static final SoundEvent SOUL_ESSENCE_POPUP = registerSound("soul_essence_popup");
    public static final SoundEvent FROST_AXE_FROST_LOGS = registerSound("frost_axe_frost_logs");
    public static final SoundEvent FROST_ESSENCE_POPUP = registerSound("frost_essence_popup");
    public static final SoundEvent SPORE = registerSound("spore");
    public static final SoundEvent SOUL_HOE_GROWING = registerSound("soul_hoe_growing");
    public static final SoundEvent BOGGED_ESSENCE_POPUP = registerSound("bogged_essence_popup");
    public static final SoundEvent VOID_SWORD_EFFECT = registerSound("void_sword_effect");
    public static final SoundEvent VOID_ESSENCE_POPUP = registerSound("void_essence_popup");

    private static SoundEvent registerSound(String id) {
        Identifier identifier = MysticalUtils.newId(id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    public static void init() {}
}
