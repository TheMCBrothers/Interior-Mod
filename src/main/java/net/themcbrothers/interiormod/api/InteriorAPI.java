package net.themcbrothers.interiormod.api;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;
import net.themcbrothers.interiormod.api.furniture.FurnitureMaterial;

/**
 * @author TheMCBrothers
 */
public class InteriorAPI {
    public static final String MOD_ID = "interiormod";

    public static final ResourceKey<Registry<FurnitureMaterial>> FURNITURE_KEY =
            ResourceKey.createRegistryKey(new ResourceLocation(MOD_ID, "furniture_material"));

    private static IForgeRegistry<FurnitureMaterial> FURNITURE_REGISTRY;

    /**
     * Gets the Forge Registry for {@link FurnitureMaterial}
     *
     * @return Forge Registry
     */
    public static IForgeRegistry<FurnitureMaterial> furnitureRegistry() {
        if (FURNITURE_REGISTRY == null) {
            FURNITURE_REGISTRY = RegistryManager.ACTIVE.getRegistry(FURNITURE_KEY);
        }
        return FURNITURE_REGISTRY;
    }
}
