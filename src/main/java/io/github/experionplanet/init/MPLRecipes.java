package io.github.experionplanet.init;

import io.github.experionplanet.utils.MysticalUtils;
import io.github.experionplanet.recipe.MysticalPedestalRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class MPLRecipes {
    public static final RecipeSerializer<MysticalPedestalRecipe> MYSTICAL_PEDESTAL_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, MysticalUtils.newId("mystical_pedestal"), new MysticalPedestalRecipe.Serializer());

    public static final RecipeType<MysticalPedestalRecipe> MYSTICAL_PEDESTAL_TYPE = Registry.register(Registries.RECIPE_TYPE, MysticalUtils.newId("mystical_pedestal"), new RecipeType<MysticalPedestalRecipe>() {
        @Override
        public String toString() {
            return "mystical_pedestal";
        }
    });



    public static void bootstrap() {

    }
}
