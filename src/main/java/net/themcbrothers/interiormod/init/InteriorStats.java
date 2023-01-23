package net.themcbrothers.interiormod.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.minecraftforge.registries.RegistryObject;
import net.themcbrothers.interiormod.InteriorMod;

import static net.themcbrothers.interiormod.init.Registration.CUSTOM_STATS;

/**
 * @author TheMCBrothers
 */
public class InteriorStats {
    public static final RegistryObject<ResourceLocation> SIT_DOWN =
            CUSTOM_STATS.register("sit_down", () -> registerCustom("sit_down", StatFormatter.DEFAULT));
    public static final RegistryObject<ResourceLocation> INTERACT_WITH_FURNITURE_WORKBENCH =
            CUSTOM_STATS.register("interact_with_furniture_workbench", () -> registerCustom("interact_with_furniture_workbench", StatFormatter.DEFAULT));

    private static ResourceLocation registerCustom(String key, StatFormatter formatter) {
        ResourceLocation resourcelocation = InteriorMod.getId(key);
        Registry.register(BuiltInRegistries.CUSTOM_STAT, key, resourcelocation);
        Stats.CUSTOM.get(resourcelocation, formatter);
        return resourcelocation;
    }

    static void init() {
    }
}
