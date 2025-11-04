package io.github.experionplanet;

import com.mojang.serialization.JsonOps;
import eu.midnightdust.lib.config.MidnightConfig;
import io.github.experionplanet.compat.MPLMidnightConfig;
import io.github.experionplanet.entities.SoulZombieEntity;
import io.github.experionplanet.init.*;
import io.github.experionplanet.mysticalcontent.MysticalContents;
import io.github.experionplanet.utils.ExperionLogger;
import io.github.experionplanet.recipe.MysticalPedestalRecipe;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
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

	public static boolean showInfo = false;

	public static boolean onDev = false;

	@Override
	public void onInitialize() {
		// Initializes
		MPLItems.init();
		MPLComponentTypes.init();
		MPLItemGroup.init();
		MPLBlocks.init();
		MPLBlockEntities.init();
		MPLStatusEffects.init();
		MPLDamageTypes.init();
		MPLSoundEvents.init();
		MPLPotions.init();
		MPLEntities.init();

		entityAttributes();

		// Bootstraps
		MPLParticles.bootstrap();
		MPLRecipes.bootstrap();
		MysticalContents.bootstrap();
		MysticalContents.build();

		if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
			onDev = true;
			ExperionLogger.Print("Development Enviroment Detected!");
		}

		MidnightConfig.init(MOD_ID, MPLMidnightConfig.class);

		ExperionLogger.Print("Fully Initiated!");
	}
	private void entityAttributes() {
		//FabricDefaultAttributeRegistry.register(MPLEntities.SOUL_ZOMBIE, SoulZombieEntity.createAttributes());
	}

	private void testJson() {
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