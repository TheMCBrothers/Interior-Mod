package tk.themcbros.interiormod.proxy;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tk.themcbros.interiormod.InteriorMod;

public class CommonProxy {

	public CommonProxy() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::preInit);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::postInit);
	}
	
	protected void preInit(FMLCommonSetupEvent event) {
		InteriorMod.LOGGER.info("CommonProxy preInit");
	}
	
	protected void init(InterModEnqueueEvent event) {
		InteriorMod.LOGGER.info("CommonProxy init");
	}
	
	protected void postInit(InterModProcessEvent event) {
		InteriorMod.LOGGER.info("CommonProxy postInit");
	}
	
}
