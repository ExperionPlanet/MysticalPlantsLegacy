package io.github.experionplanet.mysticalplantslg.mysticalcontent;


import io.github.experionplanet.mysticalplantslg.entities.SporeEntity;
import io.github.experionplanet.mysticalplantslg.init.MPLItemTags;
import io.github.experionplanet.mysticalplantslg.init.MPLItems;
import io.github.experionplanet.mysticalplantslg.init.MPLParticles;
import io.github.experionplanet.mysticalplantslg.init.MPLStatusEffects;
import io.github.experionplanet.mysticalplantslg.items.tool.custom.BoggedShovelItem;
import io.github.experionplanet.mysticalplantslg.items.tool.custom.SoulHoeItem;
import io.github.experionplanet.mysticalplantslg.mysticalcontent.contents.ActionSlotStack;
import io.github.experionplanet.mysticalplantslg.mysticalcontent.contents.SporeContent;
import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class MysticalContents {
    public static final MysticIdMapping<SporeEntity.SporeValue> SPORE_CONTENT = new MysticIdMapping<>();
    public static final MysticIdMapping<ActionSlotStack> ACTION_SLOT_STACK = new MysticIdMapping<>();


    public static void bootstrap() {
        // Spore Content
        SPORE_CONTENT.register(MysticalUtils.newId("bog"), SporeEntity.SporeValue.of(new StatusEffectInstance(StatusEffects.POISON, 100, 1), MPLParticles.BOG_SPORE));
        SPORE_CONTENT.register(MysticalUtils.newId("void"), SporeEntity.SporeValue.of(new StatusEffectInstance(MPLStatusEffects.VOID, 100, 0), MPLParticles.VOID_SPORE));
        SPORE_CONTENT.register(MysticalUtils.newId("rooted"), SporeEntity.SporeValue.of(new StatusEffectInstance(MPLStatusEffects.ROOTED, 60, 0), MPLParticles.ROOTED_SPORE, true));
        SPORE_CONTENT.register(MysticalUtils.newId("permafrost"), SporeEntity.SporeValue.of(new StatusEffectInstance(MPLStatusEffects.PERMAFROST, 100, 0), MPLParticles.PERMAFROST_SPORE));
        SPORE_CONTENT.register(MysticalUtils.newId("possession"), SporeEntity.SporeValue.of(new StatusEffectInstance(MPLStatusEffects.POSSESSED, 180, 1), MPLParticles.POSSESSION_SPORE, true));


        // Action Slot Stack
        ACTION_SLOT_STACK.registerItem(MPLItems.BOGGED_SHOVEL, new ActionSlotStack(MPLItemTags.SOIL_FILLING, BoggedShovelItem::fillSoil));
        ACTION_SLOT_STACK.registerItem(MPLItems.SOUL_HOE, new ActionSlotStack(MPLItemTags.SOUL_FILLING, SoulHoeItem::fillings));

    }

    public static void build() {
        SPORE_CONTENT.build();
        ACTION_SLOT_STACK.build();
    }

}
