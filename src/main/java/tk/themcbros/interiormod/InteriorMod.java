package tk.themcbros.interiormod;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tk.themcbros.interiormod.api.InteriorAPI;
import tk.themcbros.interiormod.proxy.ClientProxy;
import tk.themcbros.interiormod.proxy.CommonProxy;
import tk.themcbros.interiormod.proxy.ServerProxy;
import tk.themcbros.interiormod.recipe.FurnitureRecipeManager;

/**
 * @author TheMCBrothers
 */
@Mod(InteriorMod.MOD_ID)
public class InteriorMod {
	public static final String MOD_ID = InteriorAPI.MOD_ID;
    public static final Logger LOGGER = LogManager.getFormatterLogger(MOD_ID);

    public static InteriorMod instance;
    public static CommonProxy proxy;
    
    public InteriorMod() {
        instance = this;
        
        MinecraftForge.EVENT_BUS.register(this);
        
        InteriorAPI.init(new InteriorModAPI());
        
        proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    }
    
    public static ResourceLocation getId(String path) {
    	return new ResourceLocation(MOD_ID, path);
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH)
	public void addReloadListeners(final AddReloadListenerEvent event) {
        event.addListener(new FurnitureRecipeManager(event.getDataPackRegistries().getRecipeManager()));
	}
}
