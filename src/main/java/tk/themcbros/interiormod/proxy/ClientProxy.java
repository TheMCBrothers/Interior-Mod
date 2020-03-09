package tk.themcbros.interiormod.proxy;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tk.themcbros.interiormod.InteriorMod;
import tk.themcbros.interiormod.client.renderer.SeatRenderer;
import tk.themcbros.interiormod.client.screen.FridgeScreen;
import tk.themcbros.interiormod.init.InteriorContainers;
import tk.themcbros.interiormod.init.InteriorEntities;

public class ClientProxy extends CommonProxy {

	public ClientProxy() {
		super();
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
	}
	
	private void clientSetup(FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(InteriorEntities.SEAT, SeatRenderer::new);
		
		ScreenManager.registerFactory(InteriorContainers.FRIDGE, FridgeScreen::new);
		
		InteriorMod.LOGGER.info("ClientProxy clientSetup");
	}
	
}
