package tk.themcbros.interiormod.init;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import tk.themcbros.interiormod.furniture.FurnitureRegistry;
import tk.themcbros.interiormod.furniture.FurnitureType;
import tk.themcbros.interiormod.furniture.IFurnitureMaterial;

public class InteriorItemGroup extends ItemGroup {

	public static final InteriorItemGroup INSTANCE = new InteriorItemGroup("interiormod");
	
	private Random random = new Random();
	
	public InteriorItemGroup(String label) {
		super(label);
	}

	@Override
	public ItemStack createIcon() {
		return this.chooseRandomFurniture();
	}
	
	private ItemStack chooseRandomFurniture() {
		FurnitureType type = this.pickRandomString(Arrays.asList(FurnitureType.values()));
		IFurnitureMaterial material = this.pickRandomString(FurnitureRegistry.MATERIALS.getKeys());
		return FurnitureRegistry.createItemStack(type, material, material);
	}
	
	public <T> T pickRandomString(List<T> strs) {
		return strs.get(this.random.nextInt(strs.size()));
	}

}
