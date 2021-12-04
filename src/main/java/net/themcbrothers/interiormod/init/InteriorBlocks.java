package net.themcbrothers.interiormod.init;

import com.google.common.collect.Lists;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.themcbrothers.interiormod.blocks.*;
import net.themcbrothers.interiormod.InteriorMod;

import java.util.List;

/**
 * @author TheMCBrothers
 */
public class InteriorBlocks {

    public static final List<Block> BLOCKS = Lists.newArrayList();

    public static final ChairBlock CHAIR = registerBlock("chair", new ChairBlock(BlockBehaviour.Properties.of(Material.DECORATION).strength(0.5F).sound(SoundType.WOOD)));
    public static final TableBlock TABLE = registerBlock("table", new TableBlock(BlockBehaviour.Properties.of(Material.DECORATION).strength(0.5F).sound(SoundType.WOOD)));
    public static final FridgeBlock FRIDGE = registerBlock("fridge", new FridgeBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(2.0F, 4.0F).sound(SoundType.METAL)));
    public static final NightlightLampBlock LAMP = registerBlock("lamp", new NightlightLampBlock(BlockBehaviour.Properties.of(Material.DECORATION).strength(1.0F).sound(SoundType.WOOD)));
    public static final LampOnAStickBlock LAMP_ON_A_STICK = registerBlock("lamp_on_a_stick", new LampOnAStickBlock(BlockBehaviour.Properties.of(Material.DECORATION).strength(1.0F).sound(SoundType.WOOD)));
    public static final TrashCanBlock TRASH_CAN = registerBlock("trash_can", new TrashCanBlock(BlockBehaviour.Properties.of(Material.DECORATION).strength(1.0F).sound(SoundType.LANTERN)));
    public static final DoorBlock MODERN_DOOR = registerBlock("modern_door", new DoorBlock(BlockBehaviour.Properties.of(Material.STONE, DyeColor.WHITE).strength(2.5f).sound(SoundType.STONE).dynamicShape()));
    public static final FurnitureWorkbenchBlock FURNITURE_WORKBENCH = registerBlock("furniture_workbench", new FurnitureWorkbenchBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F).sound(SoundType.WOOD)));

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
