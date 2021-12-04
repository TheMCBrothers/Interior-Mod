package net.themcbrothers.interiormod;

import net.minecraftforge.registries.IForgeRegistry;
import net.themcbrothers.interiormod.api.InteriorAPI;
import net.themcbrothers.interiormod.api.furniture.FurnitureMaterial;
import net.themcbrothers.interiormod.api.furniture.InteriorRegistries;

/**
 * @author TheMCBrothers
 */
public class InteriorModAPI implements InteriorAPI.IInteriorAPI {
    @Override
    public IForgeRegistry<FurnitureMaterial> getFurnitureMaterialRegistry() {
        return InteriorRegistries.FURNITURE_MATERIALS;
    }
}
