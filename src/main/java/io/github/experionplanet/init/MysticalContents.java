package io.github.experionplanet.init;

import io.github.experionplanet.registry.MysticIdMapping;
import io.github.experionplanet.registry.MysticSingleMapping;
import io.github.experionplanet.registry.TrxContent;
import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class MysticalContents {
    public static final MysticIdMapping<TrxContent> BINDING_ROCK = new MysticIdMapping<>();

    public static void bootstrap() {
        // Binding Rock Contents
        BINDING_ROCK.registerDefault(new TrxContent(0.5d, 0.5d, 0.5d, 45f));
        BINDING_ROCK.registerItem(Items.IRON_PICKAXE, new TrxContent(0.5d, 1d, 0.5d, 90f));
        BINDING_ROCK.registerItem(MPLItems.EXPERIENCE_PICKAXE, new TrxContent(0.8d, 1.25d, 0.45d, -8.0f, 12.0f, 125.0f));
        BINDING_ROCK.registerItem(MPLItems.BROKEN_EXPERIENCE_PICKAXE, new TrxContent(0.8d, 1.25d, 0.45d, -8.0f, 12.0f, 125.0f));


    }

    public static void build() {
        BINDING_ROCK.build();
    }

}
