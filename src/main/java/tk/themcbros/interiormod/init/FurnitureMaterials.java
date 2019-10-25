package tk.themcbros.interiormod.init;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import tk.themcbros.interiormod.api.InteriorAPI;
import tk.themcbros.interiormod.api.furniture.FurnitureRegistryEvent;
import tk.themcbros.interiormod.api.furniture.IFurnitureRegistry;
import tk.themcbros.interiormod.util.WoodType;

public class FurnitureMaterials {

	@Mod.EventBusSubscriber(modid = InteriorAPI.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class Registration {

		/**
		 * Example for registering materials
		 */
		@SubscribeEvent
		public static void registerBeddingMaterial(FurnitureRegistryEvent event) {
			IFurnitureRegistry materialRegistry = event.getMaterialRegistry();
			
			for(WoodType type : WoodType.values(WoodType.SubType.VANILLA)) {
				materialRegistry.registerMaterial(type.getRegistryName(), type.getTextureLocation());
			}
			
			if (ModList.get().isLoaded("biomesoplenty")) {
				for (WoodType type : WoodType.values(WoodType.SubType.BOP)) {
					materialRegistry.registerMaterial(type.getRegistryName(), type.getTextureLocation());
				}
			}
			
			if (ModList.get().isLoaded("uselessmod")) {
				for (WoodType type : WoodType.values(WoodType.SubType.USELESS)) {
					materialRegistry.registerMaterial(type.getRegistryName(), type.getTextureLocation());
				}
			}
		}
	}
	
	/**
	 * Example for registering materials
	 * Call this in your postInit (InterModProcessEvent)
	 */
	public static void registerMaterials() {
		IFurnitureRegistry materialRegistry = InteriorAPI.getInstance().getFurnitureMaterialRegistry();
		
		for(WoodType type : WoodType.values(WoodType.SubType.VANILLA)) {
			materialRegistry.registerMaterial(type.getRegistryName(), type.getTextureLocation());
		}
		
		if (ModList.get().isLoaded("biomesoplenty")) {
			for (WoodType type : WoodType.values(WoodType.SubType.BOP)) {
				materialRegistry.registerMaterial(type.getRegistryName(), type.getTextureLocation());
			}
		}
		
		if (ModList.get().isLoaded("uselessmod")) {
			for (WoodType type : WoodType.values(WoodType.SubType.USELESS)) {
				materialRegistry.registerMaterial(type.getRegistryName(), type.getTextureLocation());
			}
		}
	}
	
}
