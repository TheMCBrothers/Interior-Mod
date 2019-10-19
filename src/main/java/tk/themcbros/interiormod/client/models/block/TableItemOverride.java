package tk.themcbros.interiormod.client.models.block;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import tk.themcbros.interiormod.furniture.FurnitureRegistry;
import tk.themcbros.interiormod.furniture.IFurnitureMaterial;

public class TableItemOverride extends ItemOverrideList {

	@Override
	public IBakedModel getModelWithOverrides(IBakedModel modelOriginal, ItemStack stack, World worldIn, LivingEntity entityIn) {
		if(modelOriginal instanceof TableModel) {
			CompoundNBT tag = stack.getChildTag("textures");
			if(tag != null) {
				IFurnitureMaterial primary = FurnitureRegistry.MATERIALS.get(tag.getString("primary"));
				IFurnitureMaterial secondary = FurnitureRegistry.MATERIALS.get(tag.getString("secondary"));
				return ((TableModel) modelOriginal).getCustomModel(primary, secondary);
			}
		}
		
		return modelOriginal;
	}

}
