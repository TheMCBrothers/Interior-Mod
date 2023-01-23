package net.themcbrothers.interiormod.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import net.themcbrothers.interiormod.api.furniture.FurnitureType;
import net.themcbrothers.interiormod.items.FurnitureBlockItem;
import net.themcbrothers.interiormod.items.TooltipBlockItem;
import net.themcbrothers.interiormod.items.TooltipTallBlockItem;

import static net.themcbrothers.interiormod.init.Registration.ITEMS;

/**
 * @author TheMCBrothers
 */
public class InteriorItems {
    public static final RegistryObject<BlockItem> CHAIR = ITEMS.register("chair", () -> new FurnitureBlockItem(FurnitureType.CHAIR, new Item.Properties()));
    public static final RegistryObject<BlockItem> TABLE = ITEMS.register("table", () -> new FurnitureBlockItem(FurnitureType.TABLE, new Item.Properties()));
    public static final RegistryObject<BlockItem> FRIDGE = ITEMS.register("fridge", () -> new TooltipTallBlockItem(InteriorBlocks.FRIDGE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> LAMP = ITEMS.register("lamp", () -> new TooltipBlockItem(InteriorBlocks.LAMP.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> LAMP_ON_A_STICK = ITEMS.register("lamp_on_a_stick", () -> new TooltipTallBlockItem(InteriorBlocks.LAMP_ON_A_STICK.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> TRASH_CAN = ITEMS.register("trash_can", () -> new TooltipTallBlockItem(InteriorBlocks.TRASH_CAN.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> MODERN_DOOR = ITEMS.register("modern_door", () -> new TooltipTallBlockItem(InteriorBlocks.MODERN_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> FURNITURE_WORKBENCH = ITEMS.register("furniture_workbench", () -> new TooltipBlockItem(InteriorBlocks.FURNITURE_WORKBENCH.get(), new Item.Properties()));

    static void init() {
    }
}
