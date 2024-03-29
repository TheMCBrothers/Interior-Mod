package net.themcbrothers.interiormod.init;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.RegistryObject;
import net.themcbrothers.interiormod.blocks.*;

import static net.themcbrothers.interiormod.init.Registration.BLOCKS;

/**
 * @author TheMCBrothers
 */
public class InteriorBlocks {
    public static final RegistryObject<ChairBlock> CHAIR = BLOCKS.register("chair", () -> new ChairBlock(BlockBehaviour.Properties.of().strength(0.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<TableBlock> TABLE = BLOCKS.register("table", () -> new TableBlock(BlockBehaviour.Properties.of().strength(0.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<FridgeBlock> FRIDGE = BLOCKS.register("fridge", () -> new FridgeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(2.0F, 4.0F).sound(SoundType.METAL)));
    public static final RegistryObject<NightlightLampBlock> LAMP = BLOCKS.register("lamp", () -> new NightlightLampBlock(BlockBehaviour.Properties.of().lightLevel(state -> state.getValue(NightlightLampBlock.LIT) ? 15 : 0).strength(0.3F).sound(SoundType.GLASS)));
    public static final RegistryObject<LampOnAStickBlock> LAMP_ON_A_STICK = BLOCKS.register("lamp_on_a_stick", () -> new LampOnAStickBlock(BlockBehaviour.Properties.of().lightLevel(state -> state.getValue(LampOnAStickBlock.LIT) && state.getValue(LampOnAStickBlock.PART) == LampOnAStickBlock.Part.TOP ? 15 : 0).strength(1.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<TrashCanBlock> TRASH_CAN = BLOCKS.register("trash_can", () -> new TrashCanBlock(BlockBehaviour.Properties.of().strength(1.0F).sound(SoundType.LANTERN)));
    public static final RegistryObject<DoorBlock> MODERN_DOOR = BLOCKS.register("modern_door", () -> new DoorBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE).strength(2.5f).sound(SoundType.STONE).dynamicShape(), BlockSetType.CHERRY));
    public static final RegistryObject<FurnitureWorkbenchBlock> FURNITURE_WORKBENCH = BLOCKS.register("furniture_workbench", () -> new FurnitureWorkbenchBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.5F).sound(SoundType.WOOD)));

    static void init() {
    }
}
