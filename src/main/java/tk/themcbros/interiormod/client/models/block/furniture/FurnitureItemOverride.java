package tk.themcbros.interiormod.client.models.block.furniture;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import tk.themcbros.interiormod.api.furniture.FurnitureMaterial;
import tk.themcbros.interiormod.api.furniture.InteriorRegistries;

import javax.annotation.Nullable;

/**
 * @author TheMCBrothers
 */
public class FurnitureItemOverride extends ItemOverrideList {
    @Nullable
    @Override
    public IBakedModel getOverrideModel(IBakedModel modelOriginal, ItemStack stack, @Nullable ClientWorld worldIn, @Nullable LivingEntity entityIn) {
        if(modelOriginal instanceof FurnitureModel) {
            CompoundNBT tag = stack.getChildTag("textures");
            if(tag != null) {
                FurnitureMaterial primary = InteriorRegistries.FURNITURE_MATERIALS.getValue(ResourceLocation.tryCreate(tag.getString("primary")));
                FurnitureMaterial secondary = InteriorRegistries.FURNITURE_MATERIALS.getValue(ResourceLocation.tryCreate(tag.getString("secondary")));
                return ((FurnitureModel) modelOriginal).getCustomModel(primary, secondary);
            }
        }
        return modelOriginal;
    }
}
