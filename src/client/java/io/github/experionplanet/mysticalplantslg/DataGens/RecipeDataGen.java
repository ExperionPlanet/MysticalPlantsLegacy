package io.github.experionplanet.mysticalplantslg.DataGens;

import io.github.experionplanet.mysticalplantslg.init.MPLBlocks;
import io.github.experionplanet.mysticalplantslg.init.MPLItemTags;
import io.github.experionplanet.mysticalplantslg.init.MPLItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

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

        offerSmelting(recipeExporter, List.of(MPLItems.RAW_MYSTICAL), RecipeCategory.MISC, MPLItems.MYSTICAL_INGOT, 2f, 220, "mystical_materials");
        offerBlasting(recipeExporter, List.of(MPLItems.RAW_MYSTICAL), RecipeCategory.MISC, MPLItems.MYSTICAL_INGOT, 2f, 120, "mystical_materials");
    }
}
