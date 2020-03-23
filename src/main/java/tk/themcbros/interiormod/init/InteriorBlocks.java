package tk.themcbros.interiormod.init;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tk.themcbros.interiormod.InteriorMod;
import tk.themcbros.interiormod.blocks.ChairBlock;
import tk.themcbros.interiormod.blocks.FridgeBlock;
import tk.themcbros.interiormod.blocks.LampOnAStickBlock;
import tk.themcbros.interiormod.blocks.TableBlock;

public class InteriorBlocks {

	private static final List<Block> BLOCKS = Lists.newArrayList();
	
	public static final ChairBlock CHAIR = registerBlock("chair", new ChairBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(.5f).sound(SoundType.WOOD)));
	public static final TableBlock TABLE = registerBlock("table", new TableBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(.5f).sound(SoundType.WOOD)));
	public static final FridgeBlock FRIDGE = registerBlock("fridge", new FridgeBlock(Block.Properties.create(Material.ANVIL).hardnessAndResistance(1f).sound(SoundType.METAL)));
	public static final LampOnAStickBlock LAMP = registerBlock("lamp", new LampOnAStickBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1f).sound(SoundType.WOOD)));
	
	private static <T extends Block> T registerBlock(String registryName, T block) {
		block.setRegistryName(InteriorMod.getId(registryName));
		BLOCKS.add(block);
		return block;
	}
	
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = InteriorMod.MOD_ID)
	public static class Registration {
		@SubscribeEvent
		public static void onBlockRegistry(final RegistryEvent.Register<Block> event) {
			BLOCKS.forEach(event.getRegistry()::register);
		}
	}
	
}
