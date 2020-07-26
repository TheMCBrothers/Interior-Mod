package tk.themcbros.interiormod.init;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tk.themcbros.interiormod.InteriorMod;
import tk.themcbros.interiormod.blocks.*;

/**
 * @author TheMCBrothers
 */
public class InteriorBlocks {

    private static final List<Block> BLOCKS = Lists.newArrayList();

    public static final ChairBlock CHAIR = registerBlock("chair", new ChairBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(.5f).sound(SoundType.WOOD)));
    public static final TableBlock TABLE = registerBlock("table", new TableBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(.5f).sound(SoundType.WOOD)));
    public static final FridgeBlock FRIDGE = registerBlock("fridge", new FridgeBlock(Block.Properties.create(Material.ANVIL).hardnessAndResistance(1f).sound(SoundType.METAL)));
    public static final NightlightLampBlock LAMP = registerBlock("lamp", new NightlightLampBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1f).sound(SoundType.WOOD)));
    public static final LampOnAStickBlock LAMP_ON_A_STICK = registerBlock("lamp_on_a_stick", new LampOnAStickBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1f).sound(SoundType.WOOD)));
    public static final TrashCanBlock TRASH_CAN = registerBlock("trash_can", new TrashCanBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1f).sound(SoundType.LANTERN)));
    public static final DoorBlock MODERN_DOOR = registerBlock("modern_door", new ModernDoorBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1f).sound(SoundType.WOOD)));

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

            RenderTypeLookup.setRenderLayer(FRIDGE, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(TRASH_CAN, RenderType.getTranslucent());
            RenderTypeLookup.setRenderLayer(MODERN_DOOR, RenderType.getCutout());
        }
    }

}
