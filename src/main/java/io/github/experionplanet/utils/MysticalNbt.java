package io.github.experionplanet.utils;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MysticalNbt {
    private class IndexTemp {
       final Object baseValue;
       final String type;
       final String index;

        public IndexTemp(Object baseValue, String type, String index) {
            this.baseValue = baseValue;
            this.type = type;
            this.index = index;
        }
    }

    private Map<String,Integer> datInt;
    private Map<String,String> datString;
    private Map<String,Boolean> datBoolean;
    private Map<String,Long> datLong;
    private Map<String,IndexTemp> temp = new HashMap<>();
    private boolean hasBuild = false;

    public MysticalNbt() {}

    public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        for (Map.Entry<String, Integer> entry : datInt.entrySet()) {
            nbt.putInt(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, String> entry : datString.entrySet()) {
            nbt.putString(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, Boolean> entry : datBoolean.entrySet()) {
            nbt.putBoolean(entry.getKey(), entry.getValue());
        }
    }

    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        for (String key : datInt.keySet()) {
            if (nbt.contains(key, NbtCompound.INT_TYPE)) {
                datInt.put(key, nbt.getInt(key)) ;
            }
        }

        for (String key : datString.keySet()) {
            if (nbt.contains(key, NbtCompound.STRING_TYPE)) {
                datString.put(key, nbt.getString(key)) ;
            }
        }

        for (String key : datBoolean.keySet()) {
            if (nbt.contains(key)) {
                datBoolean.put(key, nbt.getBoolean(key)) ;
            }
        }

    }

    public void setInt(String index, int value) {
        datInt.put(index, value);
    }

    public void setString(String index, String value) {
        datString.put(index, value);
    }

    public void setBoolean(String index, boolean value) {
        datBoolean.put(index, value);
    }

    public long setLong(String index, long value) {
        return datLong.put(index, value);
    }

    public int getInt(String index) {
        return datInt.get(index);
    }

    public String getString(String index) {
        return datString.get(index);
    }

    public boolean getBoolean(String index) {
        return datBoolean.get(index);
    }

    public long getLong(String index) {
        return datLong.get(index);
    }

    public MysticalNbt propInt(String index, int baseValue) {
        temp.put(index, new IndexTemp(baseValue, "int", index));
        return this;
    }

    public MysticalNbt propBoolean(String index, boolean baseValue) {
        temp.put(index, new IndexTemp(baseValue, "bool", index));
        return this;
    }

    public MysticalNbt propString(String index, String baseValue) {
        temp.put(index, new IndexTemp(baseValue, "string", index));
        return this;
    }

    public MysticalNbt propLong(String index, long baseValue) {
        temp.put(index, new IndexTemp(baseValue, "long", index));
        return this;
    }

    public MysticalNbt build() {
        if (!hasBuild) {
            this.datInt = new HashMap<>();
            this.datBoolean = new HashMap<>();
            this.datString = new HashMap<>();
            this.datLong = new HashMap<>();

            for (Map.Entry<String, IndexTemp> entry : temp.entrySet()) {
                IndexTemp myIndex = entry.getValue();
                String name = entry.getKey();

                if (myIndex.type.equals("int")) {
                    datInt.put(name, (int) myIndex.baseValue);
                }else if (myIndex.type.equals("bool")) {
                    datBoolean.put(name, (boolean) myIndex.baseValue);
                }else if (myIndex.type.equals("string")) {
                    datString.put(name, (String) myIndex.baseValue);
                }else if (myIndex.type.equals("long")) {
                    datLong.put(name, (long) myIndex.baseValue);
                }
            }

            hasBuild = true;
        }

        return this;
    }


}
