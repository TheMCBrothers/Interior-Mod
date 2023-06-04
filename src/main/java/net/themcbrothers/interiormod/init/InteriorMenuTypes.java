package net.themcbrothers.interiormod.init;

import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.RegistryObject;
import net.themcbrothers.interiormod.container.FurnitureWorkbenchMenu;

import static net.themcbrothers.interiormod.init.Registration.MENU_TYPES;

/**
 * @author TheMCBrothers
 */
public class InteriorMenuTypes {
    public static final RegistryObject<MenuType<FurnitureWorkbenchMenu>> FURNITURE_WORKBENCH =
            MENU_TYPES.register("furniture_container", () -> new MenuType<>(FurnitureWorkbenchMenu::new, FeatureFlags.VANILLA_SET));

    static void init() {
    }
}
