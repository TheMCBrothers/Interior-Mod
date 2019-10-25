package tk.themcbros.interiormod.items;

import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.Constants;
import tk.themcbros.interiormod.api.furniture.FurnitureType;
import tk.themcbros.interiormod.furniture.FurnitureRegistry;

public class FurnitureBlockItem extends BlockItem {

	public FurnitureBlockItem(FurnitureType furniture, Properties builder) {
		super(furniture.getBlock(), builder);
	}
	
	@Override
	public ITextComponent getDisplayName(ItemStack stack) {
		if (!stack.isEmpty()) {
			if (stack.hasTag() && stack.getTag().contains("textures", Constants.NBT.TAG_COMPOUND)) {
				CompoundNBT tag = stack.getTag().getCompound("textures");
				String one = FurnitureRegistry.MATERIALS.get(tag.getString("primary")).getDisplayName().getFormattedText();
				String two = FurnitureRegistry.MATERIALS.get(tag.getString("secondary")).getDisplayName().getFormattedText();
				return new TranslationTextComponent(this.getTranslationKey(stack), !one.equalsIgnoreCase(two) ? one + "-" + two : one);
			}
		}
		return super.getDisplayName(stack);
	}
	
	@Override
	public String getTranslationKey(ItemStack stack) {
		if (!stack.isEmpty()) {
			if (stack.hasTag() && stack.getTag().contains("textures", Constants.NBT.TAG_COMPOUND)) {
				CompoundNBT tag = stack.getTag().getCompound("textures");
				if (tag.contains("primary", Constants.NBT.TAG_STRING) && tag.contains("secondary", Constants.NBT.TAG_STRING)) {
					return this.getTranslationKey() + ".name";
				}
			}
		}
		return this.getTranslationKey();
	}

}
