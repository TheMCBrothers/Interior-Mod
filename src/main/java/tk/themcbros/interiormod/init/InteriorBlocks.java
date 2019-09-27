package tk.themcbros.interiormod.init;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tk.themcbros.interiormod.InteriorMod;
import tk.themcbros.interiormod.blocks.ChairBlock;

public class InteriorBlocks {

	private static final List<Block> BLOCKS = Lists.newArrayList();
	
	public static final ChairBlock CHAIR = registerBlock("chair", new ChairBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1f)));
	
	private static <T extends Block> T registerBlock(String registryName, T block) {
		block.setRegistryName(InteriorMod.getId(registryName));
		BLOCKS.add(block);
		return block;
	}
	
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = InteriorMod.MOD_ID)
	public static class Registration {
		@SubscribeEvent
		public static void onBlockRegistry(final RegistryEvent.Register<Block> event) {
			BLOCKS.forEach(block -> event.getRegistry().register(block));
		}
	}
	
}
