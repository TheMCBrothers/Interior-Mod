package tk.themcbros.interiormod.furniture;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import tk.themcbros.interiormod.InteriorMod;

public interface IFurnitureMaterial {

	/**
	 * Texture location that for material, eg 'minecraft:block/white_wool'
	 */
	public String getTexture();

	/**
	 * The translation key using for the tooltip
	 */
	public ITextComponent getTooltip();

	/**
	 * The string saved to the item NBT and tileentity NBT, used for material
	 * lookup. This should be unique and is usually a block registry name, e.g
	 * 'minecraft:white_wool'
	 */
	public String getSaveId();

	/**
	 * The ingredient used in the crafting recipe of the closet
	 */
	default Ingredient getIngredient() {
		return Ingredient.EMPTY;
	}
	
	/**
	 * Receives the registry name, in default implementation is used
	 */
	default IFurnitureMaterial setRegName(String regName) {
		return this;
	}
	
	public static IFurnitureMaterial NULL = new IFurnitureMaterial() {
		
		@Override
		public ITextComponent getTooltip() {
			return new TranslationTextComponent("closet.null");
		}
		
		@Override
		public String getTexture() {
			return InteriorMod.MOD_ID + ":block/missing";
		}
		
		@Override
		public String getSaveId() {
			return "missing";
		}
	};
	
	public static IFurnitureMaterial getHolder(String id) {
		return new IFurnitureMaterial() {
			@Override
			public String getTexture() {
				return InteriorMod.MOD_ID + ":block/missing";
			}

			@Override
			public ITextComponent getTooltip() {
				return new TranslationTextComponent("furniture.interiormod.null");
			}

			@Override
			public String getSaveId() {
				return id;
			}
		};
	}
	
}
