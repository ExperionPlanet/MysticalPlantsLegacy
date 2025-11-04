package io.github.experionplanet.mysticalcontent.contents;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;

public class ActionSlotStack {
    public final TagKey<Item> tagKey;
    public final ActionStackPredicate predicate;

    public ActionSlotStack(TagKey<Item> holdingStack, ActionStackPredicate predicate) {
        this.tagKey = holdingStack;
        this.predicate = predicate;
    }

    public interface ActionStackPredicate {
        void action(ItemStack targetStack, ItemStack holdingStack);
    }
}
