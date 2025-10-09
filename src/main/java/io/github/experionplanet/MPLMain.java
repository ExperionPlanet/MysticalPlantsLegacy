package io.github.experionplanet;

import com.mojang.serialization.JsonOps;
import io.github.experionplanet.init.*;
import io.github.experionplanet.utils.ExperionLogger;
import io.github.experionplanet.recipe.MysticalPedestalRecipe;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MPLMain implements ModInitializer {
	public static final String MOD_ID = "mysticalplantslg";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static boolean onDev = false;

	@Override
	public void onInitialize() {
		MPLItems.init();
		MPLComponentTypes.init();
		MPLItemGroup.init();
		MPLBlocks.init();
		MPLBlockEntities.init();
		MPLParticles.bootstrap();
		MPLRecipes.bootstrap();
		MPLStatusEffects.init();
		MPLDamageTypes.init();
		MysticalContents.bootstrap();
		MysticalContents.build();

		if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
			onDev = true;
			ExperionLogger.Print("Development Enviroment Detected!");
			//testJson();
		}

		ExperionLogger.Print("Fully Initiated!");
	}

	public void testJson() {
		ItemStack output = new ItemStack(MPLItems.EXPERIENCE_PICKAXE);
		ItemStack binded = new ItemStack(MPLItems.BROKEN_EXPERIENCE_PICKAXE);
		Ingredient last = Ingredient.ofItems(Items.STICK);
		List<Ingredient> list = List.of(
				Ingredient.ofItems(MPLItems.BROKEN_EXPERIENCE_PICKAXE),
				Ingredient.ofItems(MPLItems.EXPERIENCE_ESSENCE),
				Ingredient.ofItems(MPLItems.EXPERIENCE_ESSENCE),
				Ingredient.ofItems(Items.EXPERIENCE_BOTTLE),
				Ingredient.ofItems(Items.EXPERIENCE_BOTTLE),
				Ingredient.ofItems(Items.EXPERIENCE_BOTTLE),
				Ingredient.ofItems(Items.DIAMOND_PICKAXE),
				Ingredient.EMPTY
		);
		MysticalPedestalRecipe recipe = new MysticalPedestalRecipe(list, last, binded,output);
		String json = recipe.CODEC.encodeStart(JsonOps.INSTANCE, recipe).getOrThrow().toString();
		ExperionLogger.Print("JSON CREATED: " + json);
	}
}