package tk.themcbros.interiormod.init;

import com.google.common.collect.Lists;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tk.themcbros.interiormod.InteriorMod;
import tk.themcbros.interiormod.api.furniture.FurnitureType;
import tk.themcbros.interiormod.items.FurnitureBlockItem;
import tk.themcbros.interiormod.items.TooltipBlockItem;
import tk.themcbros.interiormod.items.TooltipTallBlockItem;

import java.util.List;

/**
 * @author TheMCBrothers
 */
public class InteriorItems {

    private static final List<Item> ITEMS = Lists.newArrayList();

    public static final BlockItem CHAIR = registerItem("chair", new FurnitureBlockItem(FurnitureType.CHAIR, new Item.Properties().tab(InteriorItemGroup.INSTANCE)));
    public static final BlockItem TABLE = registerItem("table", new FurnitureBlockItem(FurnitureType.TABLE, new Item.Properties().tab(InteriorItemGroup.INSTANCE)));
    public static final BlockItem FRIDGE = registerItem("fridge", new TooltipTallBlockItem(InteriorBlocks.FRIDGE, new Item.Properties().tab(InteriorItemGroup.INSTANCE)));
    public static final BlockItem LAMP = registerItem("lamp", new TooltipBlockItem(InteriorBlocks.LAMP, new Item.Properties().tab(InteriorItemGroup.INSTANCE)));
    public static final BlockItem LAMP_ON_A_STICK = registerItem("lamp_on_a_stick", new TooltipTallBlockItem(InteriorBlocks.LAMP_ON_A_STICK, new Item.Properties().tab(InteriorItemGroup.INSTANCE)));
    public static final BlockItem TRASH_CAN = registerItem("trash_can", new TooltipTallBlockItem(InteriorBlocks.TRASH_CAN, new Item.Properties().tab(InteriorItemGroup.INSTANCE)));
    public static final BlockItem MODERN_DOOR = registerItem("modern_door", new TooltipTallBlockItem(InteriorBlocks.MODERN_DOOR, new Item.Properties().tab(InteriorItemGroup.INSTANCE)));
    public static final BlockItem FURNITURE_WORKBENCH = registerItem("furniture_workbench", new TooltipBlockItem(InteriorBlocks.FURNITURE_WORKBENCH, new Item.Properties().tab(InteriorItemGroup.INSTANCE)));

    private static <T extends Item> T registerItem(String registryName, T item) {
        item.setRegistryName(InteriorMod.getId(registryName));
        ITEMS.add(item);
        return item;
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = InteriorMod.MOD_ID)
    public static class Registration {
        @SubscribeEvent
        public static void onItemRegistry(final RegistryEvent.Register<Item> event) {
            ITEMS.forEach(event.getRegistry()::register);
        }
    }

}
