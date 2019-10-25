package tk.themcbros.interiormod;

import tk.themcbros.interiormod.api.InteriorAPI.IInteriorAPI;
import tk.themcbros.interiormod.api.furniture.IFurnitureRegistry;
import tk.themcbros.interiormod.furniture.FurnitureRegistry;

public class InteriorModAPI implements IInteriorAPI {

	@Override
	public IFurnitureRegistry getFurnitureMaterialRegistry() {
		return FurnitureRegistry.MATERIALS;
	}

}
