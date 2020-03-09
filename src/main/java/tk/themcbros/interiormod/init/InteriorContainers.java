package tk.themcbros.interiormod.init;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tk.themcbros.interiormod.InteriorMod;
import tk.themcbros.interiormod.container.FridgeContainer;

public class InteriorContainers {

private static final List<ContainerType<?>> CONTAINERS = Lists.newArrayList();
	
	public static final ContainerType<FridgeContainer> FRIDGE = registerContainerType("fridge", new ContainerType<>(FridgeContainer::new));
	
	private static <T extends ContainerType<?>> T registerContainerType(String registryName, T containerT) {
		containerT.setRegistryName(InteriorMod.getId(registryName));
		CONTAINERS.add(containerT);
		return containerT;
	}
	
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = InteriorMod.MOD_ID)
	public static class Registration {
		@SubscribeEvent
		public static void onBlockRegistry(final RegistryEvent.Register<ContainerType<?>> event) {
			CONTAINERS.forEach(event.getRegistry()::register);
		}
	}
	
}
