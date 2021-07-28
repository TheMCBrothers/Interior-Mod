package tk.themcbros.interiormod.init;

import com.google.common.collect.Lists;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import tk.themcbros.interiormod.InteriorMod;
import tk.themcbros.interiormod.api.furniture.FurnitureMaterial;
import tk.themcbros.interiormod.api.furniture.FurnitureType;
import tk.themcbros.interiormod.api.furniture.InteriorRegistries;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author TheMCBrothers
 */
public class InteriorItemGroup extends CreativeModeTab {

    public static final InteriorItemGroup INSTANCE = new InteriorItemGroup(InteriorMod.MOD_ID);

    private final Random random = new Random();

    public InteriorItemGroup(String label) {
        super(label);
    }

    @Override
    public ItemStack makeIcon() {
        return this.chooseRandomFurniture();
    }

    private ItemStack chooseRandomFurniture() {
        FurnitureType type = this.pickRandomString(Arrays.asList(FurnitureType.values()));
        FurnitureMaterial material = this.pickRandomString(Lists.newArrayList(InteriorRegistries.FURNITURE_MATERIALS));
        return FurnitureMaterials.createItemStack(type, material, material);
    }

    public <T> T pickRandomString(List<T> items) {
        return items.get(this.random.nextInt(items.size()));
    }

}
