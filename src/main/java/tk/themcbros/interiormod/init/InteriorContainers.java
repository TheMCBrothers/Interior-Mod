package tk.themcbros.interiormod.init;

import com.google.common.collect.Lists;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tk.themcbros.interiormod.InteriorMod;
import tk.themcbros.interiormod.container.FurnitureWorkbenchContainer;

import java.util.List;

/**
 * @author TheMCBrothers
 */
public class InteriorContainers {

    public static final List<ContainerType<?>> CONTAINER_TYPES = Lists.newArrayList();

    public static final ContainerType<FurnitureWorkbenchContainer> FURNITURE_WORKBENCH = register("furniture_container",
            new ContainerType<>(FurnitureWorkbenchContainer::new));

    private static <T extends Container> ContainerType<T> register(String registryName, ContainerType<T> containerType) {
        containerType.setRegistryName(InteriorMod.getId(registryName));
        CONTAINER_TYPES.add(containerType);
        return containerType;
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = InteriorMod.MOD_ID)
    public static class Registration {
        @SubscribeEvent
        public static void onBlockRegistry(final RegistryEvent.Register<ContainerType<?>> event) {
            CONTAINER_TYPES.forEach(event.getRegistry()::register);
        }
    }

}
