package io.github.experionplanet.init;

import io.github.experionplanet.items.ExperiencePickaxeItem;
import io.github.experionplanet.misc.HudProgressItem;
import io.github.experionplanet.registry.MysticIdMapping;
import io.github.experionplanet.utils.ExperionUtils;

public class MysticalContentsClient {
    public static final MysticIdMapping<HudProgressItem> HUD_PROGRESS_ITEM = new MysticIdMapping<>();

    public static void bootstrap() {
        HUD_PROGRESS_ITEM.registerItem(MPLItems.EXPERIENCE_PICKAXE, new HudProgressItem(
                "experience_pickaxe_hud",
                "experience_pickaxe_hud_fluid",
                ExperiencePickaxeItem.EXP_FILLS,
                ExperiencePickaxeItem.MAX_FILLS
        ));
    }

    public static void build() {
        HUD_PROGRESS_ITEM.build();
    }
}
