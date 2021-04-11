package tk.themcbros.interiormod.init;

import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import tk.themcbros.interiormod.InteriorMod;

/**
 * @author TheMCBrothers
 */
public class InteriorStats {
    public static final ResourceLocation SIT_DOWN = registerCustom("sit_down", IStatFormatter.DEFAULT);
    public static final ResourceLocation INTERACT_WITH_FURNITURE_WORKBENCH = registerCustom("interact_with_furniture_workbench", IStatFormatter.DEFAULT);

    private static ResourceLocation registerCustom(String key, IStatFormatter formatter) {
        ResourceLocation resourcelocation = InteriorMod.getId(key);
        Registry.register(Registry.CUSTOM_STAT, key, resourcelocation);
        Stats.CUSTOM.get(resourcelocation, formatter);
        return resourcelocation;
    }
}
