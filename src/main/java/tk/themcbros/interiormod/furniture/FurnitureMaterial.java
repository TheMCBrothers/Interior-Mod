package tk.themcbros.interiormod.furniture;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;

public class FurnitureMaterial implements IFurnitureMaterial {

	@Nullable
	private String translationKey;

	public ResourceLocation key;
	public String textureLoc;
	public Ingredient ingredients;
	public String regName;

	public FurnitureMaterial(ResourceLocation key, ResourceLocation texture, Ingredient ingredients) {
		this.key = key;
		this.textureLoc = texture.toString();
		this.ingredients = ingredients;
	}

	public FurnitureMaterial(Block block, ResourceLocation texture, Ingredient ingredients) {
		this(ForgeRegistries.BLOCKS.getKey(block), texture, ingredients);
	}

	@Override
	public String getTexture() {
		return this.textureLoc;
	}

	@Override
	public Ingredient getIngredient() {
		return this.ingredients;
	}

	@Override
	public ITextComponent getTooltip() {
		if (this.translationKey == null) {
			this.translationKey = Util.makeTranslationKey("closet." + this.regName, this.key);
		}
		return new TranslationTextComponent(this.translationKey);
	}

	@Override
	public String getSaveId() {
		return this.key.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (!(o instanceof FurnitureMaterial)) {
			return false;
		}

		FurnitureMaterial other = (FurnitureMaterial) o;
		return other.key.equals(this.key);
	}

	@Override
	public int hashCode() {
		return this.key.hashCode();
	}

	@Override
	public FurnitureMaterial setRegName(String regName) {
		this.regName = regName;
		return this;
	}

}
