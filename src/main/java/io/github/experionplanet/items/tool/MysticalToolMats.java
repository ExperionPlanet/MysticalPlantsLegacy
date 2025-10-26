package io.github.experionplanet.items.tool;

import io.github.experionplanet.init.MPLItems;
import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

import java.util.function.Supplier;

public enum MysticalToolMats implements ToolMaterial {
    EXPERIENCE(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1249, 8f, 3.0f, 40, () -> Ingredient.ofItems(MPLItems.EXPERIENCE_ESSENCE)),
    FROST(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1092, 8.5f, 4.5f, 12, () -> Ingredient.ofItems(MPLItems.FROST_ESSENCE)),
    BOGGED(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1269, 8.25f, 2.5f, 7, () -> Ingredient.ofItems(MPLItems.BOGGED_ESSENCE)),
    SOUL(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 985, 8f, 3f, 15, () -> Ingredient.ofItems(MPLItems.SOUL_ESSENCE));

    private final TagKey<Block> inverseTag;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    MysticalToolMats(TagKey<Block> inverseTag, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
        this.inverseTag = inverseTag;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
    }

    public int getDurability() {
        return this.itemDurability;
    }

    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public TagKey<Block> getInverseTag() {
        return this.inverseTag;
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public Ingredient getRepairIngredient() {
        return (Ingredient)this.repairIngredient.get();
    }
}
