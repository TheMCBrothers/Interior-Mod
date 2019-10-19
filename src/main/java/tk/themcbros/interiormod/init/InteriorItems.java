package tk.themcbros.interiormod.init;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tk.themcbros.interiormod.InteriorMod;
import tk.themcbros.interiormod.furniture.FurnitureType;
import tk.themcbros.interiormod.items.FurnitureBlockItem;

public class InteriorItems {

	private static final List<Item> ITEMS = Lists.newArrayList();
	
	public static final BlockItem CHAIR = registerItem("chair", new FurnitureBlockItem(FurnitureType.CHAIR, new Item.Properties().group(InteriorItemGroup.INSTANCE)));
	public static final BlockItem TABLE = registerItem("table", new FurnitureBlockItem(FurnitureType.TABLE, new Item.Properties().group(InteriorItemGroup.INSTANCE)));
	
	private static <T extends Item> T registerItem(String registryName, T item) {
		item.setRegistryName(InteriorMod.getId(registryName));
		ITEMS.add(item);
		return item;
	}
	
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = InteriorMod.MOD_ID)
	public static class Registration {
		@SubscribeEvent
		public static void onItemRegistry(final RegistryEvent.Register<Item> event) {
			ITEMS.forEach(item -> event.getRegistry().register(item));
		}
	}
	
}
