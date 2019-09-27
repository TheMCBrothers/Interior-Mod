package tk.themcbros.interiormod.proxy;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tk.themcbros.interiormod.InteriorMod;

public class ClientProxy extends CommonProxy {

	public ClientProxy() {
		super();
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
	}
	
	private void clientSetup(FMLClientSetupEvent event) {
		InteriorMod.LOGGER.info("ClientProxy clientSetup");
	}
	
}
