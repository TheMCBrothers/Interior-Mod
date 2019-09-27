package tk.themcbros.interiormod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import tk.themcbros.interiormod.proxy.ClientProxy;
import tk.themcbros.interiormod.proxy.CommonProxy;
import tk.themcbros.interiormod.proxy.ServerProxy;

@Mod(InteriorMod.MOD_ID)
public class InteriorMod
{
	public static final String MOD_ID = "interiormod";
    public static final Logger LOGGER = LogManager.getFormatterLogger(MOD_ID);

    public static InteriorMod instance;
    public static CommonProxy proxy;
    
    public InteriorMod() {
        instance = this;
        proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    }
    
    public static ResourceLocation getId(String path) {
    	return new ResourceLocation(MOD_ID, path);
    }
}
