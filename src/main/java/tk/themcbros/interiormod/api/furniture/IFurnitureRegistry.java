package tk.themcbros.interiormod.api.furniture;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

public interface IFurnitureRegistry extends Iterable<IFurnitureMaterial> {

	/**
	 * Creates and registers an IFurnitureMaterial instance
	 * 
	 * @param block           Used for the material id and crafting item
	 * @param textureLocation The resource location of the texture to be used
	 * @return The created IFurnitureMaterial instance
	 */
	public IFurnitureMaterial registerMaterial(@Nonnull Block block, ResourceLocation textureLocation);

	/**
	 * Creates and registers an IFurnitureMaterial instance
	 * 
	 * @param key             Used for the material id
	 * @param textureLocation The resource location of the texture to be used
	 * @return The created IFurnitureMaterial instance
	 */
	public IFurnitureMaterial registerMaterial(ResourceLocation key, ResourceLocation textureLocation);

	/**
	 * Registers an IFurnitureMaterial instance
	 */
	public IFurnitureMaterial registerMaterial(@Nonnull IFurnitureMaterial material);

}
