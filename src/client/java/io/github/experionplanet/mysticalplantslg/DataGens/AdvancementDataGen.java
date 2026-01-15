package io.github.experionplanet.mysticalplantslg.DataGens;

import io.github.experionplanet.mysticalplantslg.MPLMain;
import io.github.experionplanet.mysticalplantslg.init.MPLBlocks;
import io.github.experionplanet.mysticalplantslg.init.MPLItemTags;
import io.github.experionplanet.mysticalplantslg.init.MPLItems;
import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AdvancementDataGen extends FabricAdvancementProvider {
    public AdvancementDataGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    private static final String advancetranslate = "advancements." + MPLMain.MOD_ID + ".";

    @Override
    public void generateAdvancement(RegistryWrapper.WrapperLookup wrapperLookup, Consumer<AdvancementEntry> consumer) {
        AdvancementEntry Beginning = Advancement.Builder.create()
                .display(
                        MPLBlocks.LARGE_EXP_MUSHROOMS.asItem(),
                        textTranslate("mystical_plants_beginning"),
                        textTranslate("mystical_plants_beginning.desc"),
                        MysticalUtils.newId("textures/gui/advancement_background.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("picking_plant_first_time", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(MPLItemTags.MYSTICAL_PLANTS)))
                .build(consumer, MysticalUtils.newId("mystical_plants_beginning").toString());

        AdvancementBuilder builder = new AdvancementBuilder(consumer);

        AdvancementEntry ExperiencePlants = builder.create(
                MPLBlocks.SMALL_EXP_MUSHROOMS.asItem(),
                "experience_plants",
                Beginning,
                true,
                true,
                false,
                AdvancementFrame.TASK,
                "found_experience_plants",
                invCriterion(ItemPredicate.Builder.create().tag(MPLItemTags.EXPERIENCE_PLANTS).build())
        );
        AdvancementEntry FrostPlants = builder.create(
                MPLBlocks.GLACIER_PASSION_FLOWER.asItem(),
                "frost_plants",
                Beginning,
                true,
                true,
                false,
                AdvancementFrame.TASK,
                "found_frost_plants",
                invCriterion(ItemPredicate.Builder.create().tag(MPLItemTags.FROST_PLANTS).build())
        );
        AdvancementEntry BoggedPlants = builder.create(
                MPLBlocks.BOGSPORE_CAP.asItem(),
                "bogged_plants",
                Beginning,
                true,
                true,
                false,
                AdvancementFrame.TASK,
                "found_bogged_plants",
                invCriterion(ItemPredicate.Builder.create().tag(MPLItemTags.BOGGED_PLANTS).build())
        );
        AdvancementEntry SoulPlants = builder.create(
                MPLBlocks.SOUL_POSSESSION_IRIS.asItem(),
                "soul_plants",
                Beginning,
                true,
                true,
                false,
                AdvancementFrame.TASK,
                "found_soul_plants",
                invCriterion(ItemPredicate.Builder.create().tag(MPLItemTags.SOUL_PLANTS).build())
        );
        AdvancementEntry VoidPlants = builder.create(
                MPLBlocks.VOID_MUSHROOM.asItem(),
                "void_plants",
                Beginning,
                true,
                true,
                false,
                AdvancementFrame.TASK,
                "found_void_plants",
                invCriterion(ItemPredicate.Builder.create().tag(MPLItemTags.VOID_PLANTS).build())
        );
        // Obtain Essences
        builder.create(
                MPLItems.EXPERIENCE_ESSENCE,
                "experience_essence",
                ExperiencePlants,
                true,
                true,
                false,
                AdvancementFrame.TASK,
                "obtain_experience_essence",
                invCriterion(MPLItems.EXPERIENCE_ESSENCE)
        );
        builder.create(
                MPLItems.FROST_ESSENCE,
                "frost_essence",
                FrostPlants,
                true,
                true,
                false,
                AdvancementFrame.TASK,
                "obtain_frost_essence",
                invCriterion(MPLItems.FROST_ESSENCE)
        );
        builder.create(
                MPLItems.BOGGED_ESSENCE,
                "bogged_essence",
                BoggedPlants,
                true,
                true,
                false,
                AdvancementFrame.TASK,
                "obtain_bogged_essence",
                invCriterion(MPLItems.BOGGED_ESSENCE)
        );
        builder.create(
                MPLItems.SOUL_ESSENCE,
                "soul_essence",
                SoulPlants,
                true,
                true,
                false,
                AdvancementFrame.TASK,
                "obtain_soul_essence",
                invCriterion(MPLItems.SOUL_ESSENCE)
        );
        builder.create(
                MPLItems.VOID_ESSENCE,
                "void_essence",
                VoidPlants,
                true,
                true,
                false,
                AdvancementFrame.TASK,
                "obtain_void_essence",
                invCriterion(MPLItems.VOID_ESSENCE)
        );

        // Crafting Mystical Tools
        AdvancementEntry MysticalDust = builder.create(
                MPLItems.MYSTICAL_DUST.asItem(),
                "mystical_dust",
                Beginning,
                true,
                true,
                false,
                AdvancementFrame.TASK,
                "found_mystical_dust",
                invCriterion(MPLItems.MYSTICAL_DUST)
        );

        AdvancementEntry StarEssence = builder.create(
                MPLItems.MYSTICAL_STAR_ESSENCE.asItem(),
                "mystical_star_essence",
                MysticalDust,
                true,
                true,
                false,
                AdvancementFrame.CHALLENGE,
                "craft_mystical_star_essence",
                invCriterion(MPLItems.MYSTICAL_STAR_ESSENCE)
        );

        builder.create(
                MPLItems.EXPERIENCE_PICKAXE.asItem(),
                "experience_pickaxe",
                StarEssence,
                true,
                true,
                false,
                AdvancementFrame.CHALLENGE,
                "craft_experience_pickaxe",
                invCriterion(MPLItems.EXPERIENCE_PICKAXE)
        );

        builder.create(
                MPLItems.FROST_AXE.asItem(),
                "frost_axe",
                StarEssence,
                true,
                true,
                false,
                AdvancementFrame.CHALLENGE,
                "craft_frost_axe",
                invCriterion(MPLItems.FROST_AXE)
        );

        builder.create(
                MPLItems.BOGGED_SHOVEL.asItem(),
                "bogged_shovel",
                StarEssence,
                true,
                true,
                false,
                AdvancementFrame.CHALLENGE,
                "craft_bogged_shovel",
                invCriterion(MPLItems.BOGGED_SHOVEL)
        );

        builder.create(
                MPLItems.SOUL_HOE.asItem(),
                "soul_hoe",
                StarEssence,
                true,
                true,
                false,
                AdvancementFrame.CHALLENGE,
                "craft_soul_hoe",
                invCriterion(MPLItems.SOUL_HOE)
        );

        builder.create(
                MPLItems.VOID_SWORD.asItem(),
                "void_sword",
                StarEssence,
                true,
                true,
                false,
                AdvancementFrame.CHALLENGE,
                "craft_void_sword",
                invCriterion(MPLItems.VOID_SWORD)
        );

    }

    private static AdvancementCriterion<InventoryChangedCriterion.Conditions> invCriterion(ItemConvertible... itemPredicates) {
        return InventoryChangedCriterion.Conditions.items(itemPredicates);
    }

    private static AdvancementCriterion<InventoryChangedCriterion.Conditions> invCriterion(ItemPredicate... itemPredicates) {
        return InventoryChangedCriterion.Conditions.items(itemPredicates);
    }

    private static class AdvancementBuilder {
        private final Consumer<AdvancementEntry> consumer;


        public AdvancementBuilder(Consumer<AdvancementEntry> v) {
            this.consumer = v;
        }

        public AdvancementEntry create(Item display, String name, @Nullable AdvancementEntry parent, boolean showToast, boolean announceChat, boolean hidden, AdvancementFrame frame, String criterionName, AdvancementCriterion<?> criterion) {
            return Advancement.Builder.create()
                    .parent(parent)
                    .display(
                            display,
                            textTranslate(name),
                            textTranslate(name + ".desc"),
                            null,
                            frame,
                            showToast,
                            announceChat,
                            hidden
                    )
                    .criterion(criterionName, criterion)
                    .build(consumer, MysticalUtils.newId(name).toString());
        }

    }

    public static MutableText textTranslate(String str1) {
        return Text.translatable(advancetranslate + str1);
    }

}
