package io.github.experionplanet.registry;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.github.experionplanet.utils.ExperionLogger;

public class MysticSingleMapping<T> {
    public boolean hasBuild = false;
    private ImmutableSet.Builder<T> builder = ImmutableSet.builder();
    private ImmutableSet<T> map;

    public void register(T value) {
        builder.add(value);
    }

    public void build() {
        this.map = builder.build();
        this.hasBuild = true;
    }

    public boolean contains(T value) {
        if (this.hasBuild) {
            return this.map.contains(value);
        }

        return false;
    }
}
