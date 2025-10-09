package io.github.experionplanet.registry;

import io.github.experionplanet.utils.ExperionUtils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class MysticIdMapping<T> extends MysticMapping<Identifier, T> {
    private boolean hasDefault = false;
    public static final Identifier DEFAULT_ID = ExperionUtils.newId("default_value");

    public void registerItem(Item item, T value) {
        register(Registries.ITEM.getId(item), value);
    }

    public void registerBlock(Block block, T value) {
        register(Registries.BLOCK.getId(block), value);
    }

    public void registerDefault(T value) {
        register(DEFAULT_ID, value);
        this.hasDefault = true;
    }

    public boolean containsItem(Item item) {
        Identifier id = Registries.ITEM.getId(item);

        return super.containsKey(id);
    }

    public T getAsItem(Item item) {
        return get(Registries.ITEM.getId(item));
    }

    @Override
    public T get(Identifier key) {
        if (super.get(key) == null) {
            if (this.hasDefault) {
                return super.get(DEFAULT_ID);
            }else {
                return null;
            }
        }

        return super.get(key);
    }
}
