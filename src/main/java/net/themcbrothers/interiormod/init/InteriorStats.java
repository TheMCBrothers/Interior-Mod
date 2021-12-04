package net.themcbrothers.interiormod.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.themcbrothers.interiormod.InteriorMod;

/**
 * @author TheMCBrothers
 */
public class InteriorStats {
    public static final ResourceLocation SIT_DOWN = registerCustom("sit_down", StatFormatter.DEFAULT);
    public static final ResourceLocation INTERACT_WITH_FURNITURE_WORKBENCH = registerCustom("interact_with_furniture_workbench", StatFormatter.DEFAULT);

    private static ResourceLocation registerCustom(String key, StatFormatter formatter) {
        ResourceLocation resourcelocation = InteriorMod.getId(key);
        Registry.register(Registry.CUSTOM_STAT, key, resourcelocation);
        Stats.CUSTOM.get(resourcelocation, formatter);
        return resourcelocation;
    }
}
