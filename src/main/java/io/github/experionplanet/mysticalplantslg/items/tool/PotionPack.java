package io.github.experionplanet.mysticalplantslg.items.tool;

import io.github.experionplanet.mysticalplantslg.utils.ExperionTranslate;
import io.github.experionplanet.mysticalplantslg.utils.MysticalUtils;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class PotionPack {
    public final Potion MAIN;
    public final Potion LONG;
    public final Potion STRONG;

    public final String name;

    public PotionPack(String index, RegistryEntry<StatusEffect> effect, EffectSettings set) {
        this.MAIN = register(index, new StatusEffectInstance(effect, set.durMain, set.ampMain));
        if (!set.noLong) {
            this.LONG = register("long_" + index,new StatusEffectInstance(effect, set.durLong, set.ampLong));
        }else {
            this.LONG = null;
        }

        if (!set.noStrong) {
            this.STRONG = register("strong_" + index,new StatusEffectInstance(effect, set.durStrong, set.ampStrong));
        }else {
            this.STRONG = null;
        }
        this.name = index;
    }

    public void translate(ExperionTranslate tr) {
        allTranslate(tr, name, name);

        if (LONG != null) {
            allTranslate(tr, "long_" + name, name);
        }

        if (STRONG != null) {
            allTranslate(tr, "strong_" + name, name);
        }
    }

    private static void allTranslate(ExperionTranslate tr, String Name, String mainName) {
        String m = "item.minecraft.";
        String base = "of " + ExperionTranslate.quickTranslate(mainName);

        tr.trRaw(m + "potion.effect." + Name, "Potion " + base);
        tr.trRaw(m + "lingering_potion.effect." + Name,"Lingering Potion " + base);
        tr.trRaw(m + "splash_potion.effect." + Name, "Splash Potion " + base);
    }

    private static Potion register(String id, StatusEffectInstance instr) {

        return Registry.register(Registries.POTION, MysticalUtils.newId(id), new Potion(instr));
    }

    public static class EffectSettings {
        public int durMain = 20;
        public int ampMain = 0;
        public int durLong = 60;
        public int ampLong = 0;
        public int durStrong = 10;
        public int ampStrong = 1;

        public boolean noLong = false;
        public boolean noStrong = false;

        public EffectSettings() {};

        public EffectSettings setMain(int dur, int amp) {
            this.durMain = dur;
            this.ampMain = amp;
            return this;
        }

        public EffectSettings setLong(int dur, int amp) {
            this.durLong = dur;
            this.ampLong = amp;
            return this;
        }

        public EffectSettings setStrong(int dur, int amp) {
            this.durStrong = dur;
            this.ampStrong = amp;
            return this;
        }

        public EffectSettings disableLong() {
            this.noLong = true;
            return this;
        }

        public EffectSettings disableStrong() {
            this.noStrong = true;
            return this;
        }
    }
}
