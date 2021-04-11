package tk.themcbros.interiormod.init;

import com.google.common.collect.Lists;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.DyeColor;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tk.themcbros.interiormod.InteriorMod;
import tk.themcbros.interiormod.blocks.*;

import java.util.List;

/**
 * @author TheMCBrothers
 */
public class InteriorBlocks {

    private static final List<Block> BLOCKS = Lists.newArrayList();

    public static final ChairBlock CHAIR = registerBlock("chair", new ChairBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final TableBlock TABLE = registerBlock("table", new TableBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
    public static final FridgeBlock FRIDGE = registerBlock("fridge", new FridgeBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).setRequiresTool().hardnessAndResistance(2.0F, 4.0F).sound(SoundType.METAL)));
    public static final NightlightLampBlock LAMP = registerBlock("lamp", new NightlightLampBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.0F).sound(SoundType.WOOD)));
    public static final LampOnAStickBlock LAMP_ON_A_STICK = registerBlock("lamp_on_a_stick", new LampOnAStickBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.0F).sound(SoundType.WOOD)));
    public static final TrashCanBlock TRASH_CAN = registerBlock("trash_can", new TrashCanBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1.0F).sound(SoundType.LANTERN)));
    public static final DoorBlock MODERN_DOOR = registerBlock("modern_door", new DoorBlock(AbstractBlock.Properties.create(Material.ROCK, DyeColor.WHITE).hardnessAndResistance(2.5f).sound(SoundType.STONE).notSolid()));
    public static final FurnitureWorkbenchBlock FURNITURE_WORKBENCH = registerBlock("furniture_workbench", new FurnitureWorkbenchBlock(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD)));

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
