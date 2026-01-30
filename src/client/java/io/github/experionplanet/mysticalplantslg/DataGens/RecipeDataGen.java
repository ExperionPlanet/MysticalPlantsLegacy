package io.github.experionplanet.mysticalplantslg.DataGens;

import io.github.experionplanet.mysticalplantslg.init.MPLBlocks;
import io.github.experionplanet.mysticalplantslg.init.MPLItemTags;
import io.github.experionplanet.mysticalplantslg.init.MPLItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RecipeDataGen extends FabricRecipeProvider {
    public RecipeDataGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter recipeExporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, MPLItems.RAW_MYSTICAL, 1)
                .input('A', MPLItems.MYSTICAL_DUST)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .group("mystical_materials")
                .criterion(FabricRecipeProvider.hasItem(MPLItems.MYSTICAL_DUST), FabricRecipeProvider.conditionsFromItem(MPLItems.MYSTICAL_DUST))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, MPLItems.MYSTICAL_STAR_ESSENCE, 1)
                .input('M', MPLItems.MYSTICAL_INGOT)
                .input('A', Items.DIAMOND)
                .input('E', MPLItemTags.ESSENCES)
                .pattern("AMA")
                .pattern("MEM")
                .pattern("AMA")
                .group("mystical_materials")
                .criterion(FabricRecipeProvider.hasItem(MPLItems.MYSTICAL_INGOT), FabricRecipeProvider.conditionsFromItem(MPLItems.MYSTICAL_INGOT))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, MPLItems.PERMAFROST_SNOWBALL, 1)
                .input('S', MPLItems.PERMAFROST_SNOWFLAKE)
                .pattern("SS ")
                .pattern("SS ")
                .group("permafrost_materials")
                .criterion(FabricRecipeProvider.hasItem(MPLItems.PERMAFROST_SNOWFLAKE), FabricRecipeProvider.conditionsFromItem(MPLItems.PERMAFROST_SNOWFLAKE))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MPLBlocks.PERMAFROST_SNOW_BLOCK, 1)
                .input('S', MPLItems.PERMAFROST_SNOWBALL)
                .pattern("SS ")
                .pattern("SS ")
                .group("permafrost_materials")
                .criterion(FabricRecipeProvider.hasItem(MPLItems.PERMAFROST_SNOWBALL), FabricRecipeProvider.conditionsFromItem(MPLItems.PERMAFROST_SNOWBALL))
                .offerTo(recipeExporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BREWING, MPLItems.MYSTICAL_BOTTLE, 3)
                .input('D', MPLItems.MYSTICAL_DUST)
                .input('G', Items.GLASS)
                .input('E', MPLItemTags.ESSENCES)
                .pattern(" E ")
                .pattern("DGD")
                .pattern(" D ")
                .group("mystical_materials")
                .criterion(FabricRecipeProvider.hasItem(MPLItems.MYSTICAL_DUST), FabricRecipeProvider.conditionsFromItem(MPLItems.MYSTICAL_DUST))
                .offerTo(recipeExporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BREWING, MPLItems.MYSTICAL_SPLASH)
                .input('D', MPLItems.MYSTICAL_DUST)
                .input('G', Items.GLASS)
                .input('E', MPLItemTags.ESSENCES)
                .input('W', ItemTags.PLANKS)
                .pattern(" EW")
                .pattern("GDG")
                .pattern(" G ")
                .group("mystical_materials")
                .criterion(FabricRecipeProvider.hasItem(MPLItems.MYSTICAL_DUST), FabricRecipeProvider.conditionsFromItem(MPLItems.MYSTICAL_DUST))
                .offerTo(recipeExporter);

        offerSmelting(recipeExporter, List.of(MPLItems.RAW_MYSTICAL), RecipeCategory.MISC, MPLItems.MYSTICAL_INGOT, 2f, 220, "mystical_materials");
        offerBlasting(recipeExporter, List.of(MPLItems.RAW_MYSTICAL), RecipeCategory.MISC, MPLItems.MYSTICAL_INGOT, 2f, 120, "mystical_materials");

        ShapelessRecipeJsonBuilder.create(RecipeCategory.BREWING, MPLItems.PROSPERITY_POTION)
                .input(MPLItems.MYSTICAL_BOTTLE)
                .input(MPLItems.EXPERIENCE_ESSENCE)
                .input(MPLItems.EXP_SPORE)
                .input(Items.EXPERIENCE_BOTTLE)
                .group("mystical_potions")
                .criterion(FabricRecipeProvider.hasItem(MPLItems.EXPERIENCE_ESSENCE), FabricRecipeProvider.conditionsFromItem(MPLItems.EXPERIENCE_ESSENCE))
                .offerTo(recipeExporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.BREWING, MPLItems.PROSPERITY_POTION_LONG)
                .input(MPLItems.PROSPERITY_POTION)
                .input(MPLItems.EXPERIENCE_ESSENCE)
                .input(MPLItems.EXPERIENCE_ESSENCE)
                .input(Items.EXPERIENCE_BOTTLE)
                .group("mystical_potions")
                .criterion(FabricRecipeProvider.hasItem(MPLItems.EXPERIENCE_ESSENCE), FabricRecipeProvider.conditionsFromItem(MPLItems.EXPERIENCE_ESSENCE))
                .offerTo(recipeExporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.BREWING, MPLItems.FROST_RESISTANCE_POTION)
                .input(MPLItems.MYSTICAL_BOTTLE)
                .input(MPLItems.FROST_ESSENCE)
                .input(MPLItems.PERMAFROST_SNOWFLAKE)
                .group("mystical_potions")
                .criterion(FabricRecipeProvider.hasItem(MPLItems.FROST_ESSENCE), FabricRecipeProvider.conditionsFromItem(MPLItems.FROST_ESSENCE))
                .offerTo(recipeExporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.BREWING, MPLItems.PERMAFROST_SPLASH)
                .input(MPLItems.MYSTICAL_SPLASH)
                .input(MPLItems.FROST_ESSENCE)
                .input(MPLItems.FROST_ESSENCE)
                .input(MPLItems.PERMAFROST_SNOWBALL)
                .group("mystical_potions")
                .criterion(FabricRecipeProvider.hasItem(MPLItems.FROST_ESSENCE), FabricRecipeProvider.conditionsFromItem(MPLItems.FROST_ESSENCE))
                .offerTo(recipeExporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.BREWING, MPLItems.POSSESSION_SPLASH)
                .input(MPLItems.MYSTICAL_SPLASH)
                .input(MPLItems.SOUL_ESSENCE)
                .input(MPLItems.SOUL_POLLEN)
                .group("mystical_potions")
                .criterion(FabricRecipeProvider.hasItem(MPLItems.SOUL_ESSENCE), FabricRecipeProvider.conditionsFromItem(MPLItems.SOUL_ESSENCE))
                .offerTo(recipeExporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.BREWING, Items.EXPERIENCE_BOTTLE)
                .input(Items.GLASS_BOTTLE)
                .input(MPLItems.EXP_SPORE)
                .input(MPLItems.EXP_SPORE)
                .input(MPLItems.EXP_SPORE)
                .input(MPLItems.EXP_SPORE)
                .input(MPLItems.EXP_SPORE)
                .input(MPLItems.EXP_SPORE)
                .input(MPLItems.EXP_SPORE)
                .input(MPLItems.EXPERIENCE_ESSENCE)
                .group("mystical_mats")
                .criterion(FabricRecipeProvider.hasItem(MPLItems.EXP_SPORE), FabricRecipeProvider.conditionsFromItem(MPLItems.EXP_SPORE))
                .offerTo(recipeExporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, MPLItems.BOG_FERTILIZER)
                .input(Items.BONE_MEAL)
                .input(MPLItems.BOG_CAP)
                .input(MPLItems.BOG_CAP)
                .input(MPLItems.BOG_CAP)
                .criterion(FabricRecipeProvider.hasItem(MPLItems.BOG_CAP), FabricRecipeProvider.conditionsFromItem(MPLItems.BOG_CAP))
                .offerTo(recipeExporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, MPLBlocks.VOID_MUSHROOM)
                .input(MPLItems.VOID_ROOT)
                .input(MPLItems.VOID_ROOT)
                .input(MPLItems.VOID_CAP)
                .input(MPLItems.VOID_CAP)
                .input(MPLItems.VOID_CAP)
                .criterion(FabricRecipeProvider.hasItem(MPLItems.BOG_CAP), FabricRecipeProvider.conditionsFromItem(MPLItems.BOG_CAP))
                .offerTo(recipeExporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.BREWING, MPLItems.VOID_SPLASH)
                .input(MPLItems.MYSTICAL_SPLASH)
                .input(MPLItems.VOID_ESSENCE)
                .input(MPLItems.VOID_CAP)
                .input(MPLItems.VOID_CAP)
                .group("mystical_potions")
                .criterion(FabricRecipeProvider.hasItem(MPLItems.VOID_CAP), FabricRecipeProvider.conditionsFromItem(MPLItems.VOID_CAP))
                .offerTo(recipeExporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.BREWING, MPLItems.ROOTED_SPLASH)
                .input(MPLItems.MYSTICAL_SPLASH)
                .input(MPLItems.VOID_ESSENCE)
                .input(MPLItems.VOID_ROOT)
                .input(MPLItems.VOID_ROOT)
                .group("mystical_potions")
                .criterion(FabricRecipeProvider.hasItem(MPLItems.VOID_ROOT), FabricRecipeProvider.conditionsFromItem(MPLItems.VOID_ROOT))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, MPLBlocks.PEDESTAL)
                .input('A', Blocks.COBBLESTONE)
                .input('C', Blocks.VINE)
                .input('D', MPLItems.MYSTICAL_INGOT)
                .pattern("AAA")
                .pattern("CDC")
                .pattern("AAA")
                .criterion(FabricRecipeProvider.hasItem(Blocks.VINE), FabricRecipeProvider.conditionsFromItem(Blocks.VINE))
                .offerTo(recipeExporter);
    }
}
