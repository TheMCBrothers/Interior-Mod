package tk.themcbros.interiormod.init;

import com.google.common.collect.Lists;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tk.themcbros.interiormod.InteriorMod;
import tk.themcbros.interiormod.container.FurnitureWorkbenchMenu;

import java.util.List;

/**
 * @author TheMCBrothers
 */
public class InteriorContainers {

    public static final List<MenuType<?>> CONTAINER_TYPES = Lists.newArrayList();

    public static final MenuType<FurnitureWorkbenchMenu> FURNITURE_WORKBENCH = register("furniture_container",
            new MenuType<>(FurnitureWorkbenchMenu::new));

    private static <T extends AbstractContainerMenu> MenuType<T> register(String registryName, MenuType<T> containerType) {
        containerType.setRegistryName(InteriorMod.getId(registryName));
        CONTAINER_TYPES.add(containerType);
        return containerType;
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = InteriorMod.MOD_ID)
    public static class Registration {
        @SubscribeEvent
        public static void onBlockRegistry(final RegistryEvent.Register<MenuType<?>> event) {
            CONTAINER_TYPES.forEach(event.getRegistry()::register);
        }
    }

}
