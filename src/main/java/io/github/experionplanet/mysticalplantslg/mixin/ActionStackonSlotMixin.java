package io.github.experionplanet.mysticalplantslg.mixin;

import io.github.experionplanet.mysticalplantslg.mysticalcontent.MysticalContents;
import io.github.experionplanet.mysticalplantslg.mysticalcontent.contents.ActionSlotStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenHandler.class)
public class ActionStackonSlotMixin {
    @Inject(method = "onSlotClick", at = @At("HEAD"))
    private void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        ScreenHandler handler = (ScreenHandler)(Object)this;
        if (button == 1 && actionType == SlotActionType.PICKUP) {
            Slot slot = ((ScreenHandler)(Object)this).getSlot(slotIndex);
            if (slot.hasStack()) {
                ItemStack stack = slot.getStack();
                Item item = stack.getItem();

                if (MysticalContents.ACTION_SLOT_STACK.containsItem(item)) {

                    ActionSlotStack actionSlotStack = MysticalContents.ACTION_SLOT_STACK.getAsItem(item);
                    ItemStack holdingStack = handler.getCursorStack();

                    if (holdingStack.isIn(actionSlotStack.tagKey)) {
 
                        actionSlotStack.predicate.action(stack,holdingStack);
                    }
                }



                
            }
        }
    }
}
