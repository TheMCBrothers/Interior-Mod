package net.themcbrothers.interiormod.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.themcbrothers.interiormod.InteriorMod;

/**
 * @author TheMCBrothers
 */
public class InteriorStats {
    public static final DeferredRegister<ResourceLocation> REGISTER = DeferredRegister.create(Registry.CUSTOM_STAT_REGISTRY, InteriorMod.MOD_ID);

    public static final RegistryObject<ResourceLocation> SIT_DOWN =
            REGISTER.register("sit_down", () -> registerCustom("sit_down", StatFormatter.DEFAULT));
    public static final RegistryObject<ResourceLocation> INTERACT_WITH_FURNITURE_WORKBENCH =
            REGISTER.register("interact_with_furniture_workbench", () -> registerCustom("interact_with_furniture_workbench", StatFormatter.DEFAULT));

    private static ResourceLocation registerCustom(String key, StatFormatter formatter) {
        ResourceLocation resourcelocation = InteriorMod.getId(key);
        Registry.register(Registry.CUSTOM_STAT, key, resourcelocation);
        Stats.CUSTOM.get(resourcelocation, formatter);
        return resourcelocation;
    }
}
