package tk.themcbros.interiormod;

import net.minecraftforge.registries.IForgeRegistry;
import tk.themcbros.interiormod.api.InteriorAPI.IInteriorAPI;
import tk.themcbros.interiormod.api.furniture.FurnitureMaterial;
import tk.themcbros.interiormod.api.furniture.InteriorRegistries;

/**
 * @author TheMCBrothers
 */
public class InteriorModAPI implements IInteriorAPI {
    @Override
    public IForgeRegistry<FurnitureMaterial> getFurnitureMaterialRegistry() {
        return InteriorRegistries.FURNITURE_MATERIALS;
    }
}
