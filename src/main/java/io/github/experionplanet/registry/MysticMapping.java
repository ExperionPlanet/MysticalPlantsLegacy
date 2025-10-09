package io.github.experionplanet.registry;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.github.experionplanet.utils.ExperionLogger;
import net.minecraft.util.Identifier;

import java.util.Map;

public class MysticMapping<T,B> {
    public boolean hasBuild = false;
    private ImmutableMap.Builder<T,B> builder = ImmutableMap.builder();
    private ImmutableMap<T,B> map;

    public void register(T key, B value) {
        builder.put(key, value);
    }

    public void build() {
        this.map = builder.build();
        this.hasBuild = true;
    }

    public B get(T key) {
        if (hasBuild) {
            if (this.map.containsKey(key)) {
                return this.map.get(key);
            }else {
                if (key instanceof Identifier) {
                    ExperionLogger.Print("MappingError: " + key.toString());
                }
                ExperionLogger.Print("MappingError: This key doesnt exist!");
            }
        }else {
            ExperionLogger.Print("MappingError: Hasn't Build yet!");
        }

        return null;
    }

    public boolean containsKey(T key) {
        if (hasBuild) {
            return this.map.containsKey(key);
        }else {
            ExperionLogger.Print("MappingError: Hasn't Build yet!");
        }

        return false;
    }

    public ImmutableSet<T> keySet() {
        return map.keySet();
    }

    public ImmutableCollection<B> valueColl() {
        return map.values();
    }
}
