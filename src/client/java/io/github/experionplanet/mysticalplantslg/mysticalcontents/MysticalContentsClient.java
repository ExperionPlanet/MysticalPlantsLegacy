package io.github.experionplanet.mysticalplantslg.mysticalcontents;

import io.github.experionplanet.mysticalplantslg.init.MPLComponentTypes;
import io.github.experionplanet.mysticalplantslg.init.MPLItems;
import io.github.experionplanet.mysticalplantslg.items.tool.custom.BoggedShovelItem;
import io.github.experionplanet.mysticalplantslg.items.tool.custom.ExperiencePickaxeItem;
import io.github.experionplanet.mysticalplantslg.mysticalcontents.content.HudProgressItem;
import io.github.experionplanet.mysticalplantslg.mysticalcontents.content.RingedBloomingContent;
import io.github.experionplanet.mysticalplantslg.mysticalcontent.MysticIdMapping;
import io.github.experionplanet.mysticalplantslg.mysticalcontent.MysticMapping;
import io.github.experionplanet.mysticalplantslg.mysticalcontents.content.TrxContent;
import net.minecraft.item.Items;

public class MysticalContentsClient {
    public static final MysticIdMapping<HudProgressItem> HUD_PROGRESS_ITEM = new MysticIdMapping<>();
    public static final MysticMapping<String, RingedBloomingContent> RINGED_BLOOMING_CONTENT = new MysticMapping<>();
    public static final MysticIdMapping<TrxContent> BINDING_ROCK = new MysticIdMapping<>();

    public static void bootstrap() {
        // Hud Progress
        HUD_PROGRESS_ITEM.registerItem(MPLItems.EXPERIENCE_PICKAXE, new HudProgressItem( // Experience Pickaxe
                "experience_pickaxe_hud",
                "experience_pickaxe_hud_fluid",
                ExperiencePickaxeItem.EXP_FILLS,
                ExperiencePickaxeItem.MAX_FILLS,
                20
        ));
        HUD_PROGRESS_ITEM.registerItem(MPLItems.BOGGED_SHOVEL, new HudProgressItem(
                "bogged_shovel_hud",
                "bogged_fluid_progress",
                MPLComponentTypes.SOIL_FILLINGS,
                BoggedShovelItem.MAX_FILLS,
                1
        ));

        // Ringed Blooming (Eg: Exbiscus, Glacier Passion Flower)
        RINGED_BLOOMING_CONTENT.register("exbiscus", new RingedBloomingContent(0.5d,1d - (2d/16d), 0.5d,0.5f,"textures/block/small_exp_ring.png", true, false));
        RINGED_BLOOMING_CONTENT.register("glacier_passion_flower", new RingedBloomingContent(.5d,1d - (1d/16d), .5d, 0.25f,"textures/block/frost_ring.png", false, true));
        RINGED_BLOOMING_CONTENT.register("hungerbalm", new RingedBloomingContent(.5d, 1d - (3d/16d), .5d, 0.3f, "textures/block/bog_ring.png", true, true));
        RINGED_BLOOMING_CONTENT.register("soul_possesion_iris", new RingedBloomingContent(0.5d, 0.5d, 0.5d, 0.25f, "textures/block/soul_ring.png", true, true));

        // Binding Rock Contents
        BINDING_ROCK.registerDefault(new TrxContent(0.5d, 0.5d, 0.5d, 45f));
        BINDING_ROCK.registerItem(Items.IRON_PICKAXE, new TrxContent(0.5d, 1d, 0.5d, 90f));
        BINDING_ROCK.registerItem(MPLItems.EXPERIENCE_PICKAXE, new TrxContent(0.8d, 1.25d, 0.45d, -8.0f, 12.0f, 125.0f));
        BINDING_ROCK.registerItem(MPLItems.BROKEN_EXPERIENCE_PICKAXE, new TrxContent(0.8d, 1.25d, 0.45d, -8.0f, 12.0f, 125.0f));
        BINDING_ROCK.registerItem(MPLItems.BROKEN_FROST_AXE, new TrxContent(0.35d, 1.3d, 0.65d, -2f, 37f, -106f));


    }

    public static void build() {
        HUD_PROGRESS_ITEM.build();
        RINGED_BLOOMING_CONTENT.build();
        BINDING_ROCK.build();
    }


}
