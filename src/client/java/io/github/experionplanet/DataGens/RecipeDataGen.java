package io.github.experionplanet.DataGens;

import io.github.experionplanet.init.MPLItemTags;
import io.github.experionplanet.init.MPLItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
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


        offerSmelting(recipeExporter, List.of(MPLItems.RAW_MYSTICAL), RecipeCategory.MISC, MPLItems.MYSTICAL_INGOT, 2f, 220, "mystical_materials");
        offerBlasting(recipeExporter, List.of(MPLItems.RAW_MYSTICAL), RecipeCategory.MISC, MPLItems.MYSTICAL_INGOT, 2f, 120, "mystical_materials");
    }
}
