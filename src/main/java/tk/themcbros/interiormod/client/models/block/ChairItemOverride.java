package tk.themcbros.interiormod.client.models.block;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

public class ChairItemOverride extends ItemOverrideList {

	@Override
	public IBakedModel getModelWithOverrides(IBakedModel modelOriginal, ItemStack stack, World worldIn, LivingEntity entityIn) {
		if(modelOriginal instanceof ChairModel) {
			CompoundNBT tag = stack.getChildTag("textures");
			if(tag != null) {
				String seatTexture = tag.getString("seatTexture");
				String texture = tag.getString("texture");
				return ((ChairModel) modelOriginal).getCustomModel(texture, seatTexture, Direction.NORTH);
			}
		}
		
		return modelOriginal;
	}

}
