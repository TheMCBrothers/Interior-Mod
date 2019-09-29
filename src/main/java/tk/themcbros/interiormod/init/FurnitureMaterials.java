package tk.themcbros.interiormod.init;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import tk.themcbros.interiormod.InteriorMod;
import tk.themcbros.interiormod.furniture.FurnitureRegistryEvent;
import tk.themcbros.interiormod.furniture.IFurnitureRegistry;
import tk.themcbros.interiormod.util.WoodType;

public class FurnitureMaterials {

	@Mod.EventBusSubscriber(modid = InteriorMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class Registration {

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
	
}
