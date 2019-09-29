package tk.themcbros.interiormod.furniture;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import tk.themcbros.interiormod.InteriorMod;

public class FurnitureRegistry implements IFurnitureRegistry {

	public static final FurnitureRegistry MATERIALS = new FurnitureRegistry("casing");

	private final List<IFurnitureMaterial> REGISTRY = new ArrayList<IFurnitureMaterial>();
	private final String key;

	public FurnitureRegistry(String key) {
		this.key = key;
	}

	@Override
	public IFurnitureMaterial registerMaterial(@Nonnull Block block, ResourceLocation textureLocation) {
		return this.registerMaterial(new FurnitureMaterial(block, textureLocation, Ingredient.fromItems(block)));
	}

	public IFurnitureMaterial registerMaterial(ResourceLocation key, ResourceLocation textureLocation) {
		return this.registerMaterial(new FurnitureMaterial(key, textureLocation,
				Ingredient.fromItems(ForgeRegistries.BLOCKS.getValue(key))));
	}

	@Override
	public IFurnitureMaterial registerMaterial(IFurnitureMaterial material) {
		if (this.REGISTRY.contains(material)) {
			InteriorMod.LOGGER.warn("Tried to register a furniture material with the id {} more that once", material);
			return null;
		} else {
			this.REGISTRY.add(material.setRegName(this.key));
			InteriorMod.LOGGER.debug("Register furniture {} under the key {}", this.key, material);
			return material;
		}
	}

	public List<IFurnitureMaterial> getKeys() {
		return this.REGISTRY;
	}

	public IFurnitureMaterial get(String saveId) {
		if (saveId.equals("missing"))
			return IFurnitureMaterial.NULL;

		// Try find a registered material
		for (IFurnitureMaterial thing : this.REGISTRY) {
			if (thing.getSaveId().equals(saveId)) {
				return thing;
			}
		}

		// Gets a holders so saveId is preserved
		return IFurnitureMaterial.getHolder(saveId);
	}

	public IFurnitureMaterial getFromStack(ItemStack stack) {
		for (IFurnitureMaterial m : this.REGISTRY) {
			if (m.getIngredient().test(stack))
				return m;
		}
		return IFurnitureMaterial.NULL;
	}

	public static ItemStack createItemStack(FurnitureType furnitureType, IFurnitureMaterial primary, IFurnitureMaterial secondary) {
		ItemStack stack = furnitureType.getStack();

		CompoundNBT tag = stack.getOrCreateChildTag("textures");
		tag.putString("primary", primary.getSaveId());
		tag.putString("secondary", secondary.getSaveId());
		return stack;
	}

}
