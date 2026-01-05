package io.github.experionplanet.mysticalcontent;


import io.github.experionplanet.init.MPLItemTags;
import io.github.experionplanet.init.MPLItems;
import io.github.experionplanet.init.MPLParticles;
import io.github.experionplanet.items.tool.custom.BoggedShovelItem;
import io.github.experionplanet.items.tool.custom.SoulHoeItem;
import io.github.experionplanet.mysticalcontent.contents.ActionSlotStack;
import io.github.experionplanet.mysticalcontent.contents.SporeContent;
import io.github.experionplanet.utils.MysticalUtils;
import net.minecraft.entity.effect.StatusEffects;

public class MysticalContents {
    public static final MysticIdMapping<SporeContent> EFFECT_SPORES = new MysticIdMapping<>();
    public static final MysticIdMapping<ActionSlotStack> ACTION_SLOT_STACK = new MysticIdMapping<>();


    public static void bootstrap() {
        // Effect Spores
        EFFECT_SPORES.register(MysticalUtils.newId("bog"), new SporeContent(MPLParticles.BOG_SPORE, StatusEffects.POISON.getIdAsString()));
        EFFECT_SPORES.register(MysticalUtils.newId("void"), new SporeContent(MPLParticles.VOID_SPORE, StatusEffects.POISON.getIdAsString()));
        // Action Slot Stack
        ACTION_SLOT_STACK.registerItem(MPLItems.BOGGED_SHOVEL, new ActionSlotStack(MPLItemTags.SOIL_FILLING, BoggedShovelItem::fillSoil));
        ACTION_SLOT_STACK.registerItem(MPLItems.SOUL_HOE, new ActionSlotStack(MPLItemTags.SOUL_FILLING, SoulHoeItem::fillings));

    }

    public static void build() {
        EFFECT_SPORES.build();
        ACTION_SLOT_STACK.build();
    }

}
