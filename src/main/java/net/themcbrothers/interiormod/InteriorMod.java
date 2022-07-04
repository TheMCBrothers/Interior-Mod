package net.themcbrothers.interiormod;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.themcbrothers.interiormod.api.InteriorAPI;
import net.themcbrothers.interiormod.proxy.ClientProxy;
import net.themcbrothers.interiormod.proxy.CommonProxy;
import net.themcbrothers.interiormod.proxy.ServerProxy;
import net.themcbrothers.interiormod.recipe.FurnitureRecipeManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

        proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    }

    public static ResourceLocation getId(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void addReloadListeners(final AddReloadListenerEvent event) {
        event.addListener(new FurnitureRecipeManager(event.getServerResources().getRecipeManager()));
    }
}
