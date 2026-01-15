package io.github.experionplanet.mysticalplantslg.compat;

import io.github.experionplanet.mysticalplantslg.init.MPLItems;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.plugins.PluginManager;
import me.shedaniel.rei.api.common.registry.ReloadStage;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.plugin.common.displays.DefaultInformationDisplay;
import me.shedaniel.rei.plugin.common.displays.brewing.BrewingRecipe;
import net.minecraft.text.Text;

public class MPLReiPlugin implements REIClientPlugin {
    // Soon...
    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.add(
                DefaultInformationDisplay.createFromEntries(EntryIngredients.of(MPLItems.BOG_CAP), Text.literal("Obtaining an Bog Cap"))
                        .line(Text.literal("Can be obtained from Bogspore Cap by using shear"))
        );
        registry.add(
                DefaultInformationDisplay.createFromEntries(EntryIngredients.of(MPLItems.VOID_ROOT), Text.literal("Obtaining an Void Root"))
                        .line(Text.literal("Can be obtained from Void Strawflower by simply stepping on them. Be careful it'll hurt you much though"))
        );
        registry.add(
                DefaultInformationDisplay.createFromEntries(EntryIngredients.of(MPLItems.VOID_CAP), Text.literal("Obtaining an Void Cap"))
                        .line(Text.literal("Can be obtained from Void Mushroom by using shear. It'll also hurt you."))
        );
    }

}
