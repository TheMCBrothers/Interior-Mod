package tk.themcbros.interiormod.proxy;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tk.themcbros.interiormod.InteriorMod;
import tk.themcbros.interiormod.data.Events;
import tk.themcbros.interiormod.init.FurnitureMaterials;

import java.lang.reflect.InvocationTargetException;

/**
 * @author TheMCBrothers
 */
public class CommonProxy {

    public CommonProxy() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        FurnitureMaterials.FURNITURE_MATERIALS.register(bus);

        bus.register(new Events());

        bus.addListener(this::preInit);
        bus.addListener(this::init);
        bus.addListener(this::postInit);
    }

    protected void preInit(FMLCommonSetupEvent event) {
        InteriorMod.LOGGER.info("CommonProxy preInit");
    }

    protected void init(InterModEnqueueEvent event) {
        InteriorMod.LOGGER.info("CommonProxy init");
        if (ModList.get().isLoaded("theoneprobe")) {
            try {
                Class.forName("tk.themcbros.interiormod.compat.top.TheOneProbeCompat").getDeclaredMethod("registerCompat").invoke(null);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException exception) {
                exception.printStackTrace();
            }
        }
    }

    protected void postInit(InterModProcessEvent event) {
        InteriorMod.LOGGER.info("CommonProxy postInit");
    }

}
