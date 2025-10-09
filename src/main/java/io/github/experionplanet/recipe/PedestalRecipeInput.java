package io.github.experionplanet.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

import java.util.List;

public class PedestalRecipeInput implements RecipeInput {
    private final List<ItemStack> stackList;
    private final ItemStack binded;
    private final ItemStack lastStack;
    
    public PedestalRecipeInput(ItemStack last, List<ItemStack> list, ItemStack bind) {
        this.lastStack = last;
        this.binded = bind;
        this.stackList = list;
    }

    public ItemStack getLastStack() {
        return this.lastStack;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return stackList.get(slot);
    }

    public List<ItemStack> getStackList() {
        return stackList;
    }

    public ItemStack getBindedStack() {
        return binded;
    }

    @Override
    public int getSize() {
        return stackList.size();
    }
}
