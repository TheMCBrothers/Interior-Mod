package net.themcbrothers.interiormod;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.themcbrothers.interiormod.api.InteriorAPI;
import net.themcbrothers.interiormod.api.furniture.FurnitureMaterial;
import net.themcbrothers.interiormod.api.furniture.FurnitureType;
import net.themcbrothers.interiormod.init.FurnitureMaterials;
import net.themcbrothers.interiormod.init.InteriorItems;
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

    private static CreativeModeTab tab;
    public static InteriorMod instance;
    public static CommonProxy proxy;

    public InteriorMod() {
        instance = this;

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::addCreativeModeTab);
        modEventBus.addListener(this::buildCreativeModeTab);

        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, this::addReloadListeners);

        proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    }

    public static ResourceLocation getId(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    private void addCreativeModeTab(final CreativeModeTabEvent.Register event) {
        tab = event.registerCreativeModeTab(getId("tab"), builder -> builder
                .title(Component.translatable("itemGroup.interiormod"))
                .icon(() -> new ItemStack(InteriorItems.FURNITURE_WORKBENCH.get())));
    }

    private void buildCreativeModeTab(final CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == tab) {
            for (FurnitureMaterial material : InteriorAPI.furnitureRegistry()) {
                event.accept(FurnitureMaterials.createItemStack(FurnitureType.TABLE, material, material));
                event.accept(FurnitureMaterials.createItemStack(FurnitureType.CHAIR, material, material));
            }
            event.accept(InteriorItems.FRIDGE);
            event.accept(InteriorItems.LAMP);
            event.accept(InteriorItems.LAMP_ON_A_STICK);
            event.accept(InteriorItems.TRASH_CAN);
            event.accept(InteriorItems.MODERN_DOOR);
            event.accept(InteriorItems.FURNITURE_WORKBENCH);
        }
    }

    private void addReloadListeners(final AddReloadListenerEvent event) {
        event.addListener(new FurnitureRecipeManager(event.getServerResources().getRecipeManager()));
    }
}
