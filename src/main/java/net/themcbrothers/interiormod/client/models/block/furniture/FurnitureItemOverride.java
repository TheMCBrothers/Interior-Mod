package net.themcbrothers.interiormod.client.models.block.furniture;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.themcbrothers.interiormod.api.furniture.FurnitureMaterial;
import net.themcbrothers.interiormod.api.furniture.InteriorRegistries;

import javax.annotation.Nullable;

/**
 * @author TheMCBrothers
 */
public class FurnitureItemOverride extends ItemOverrides {
    @Nullable
    @Override
    public BakedModel resolve(BakedModel modelOriginal, ItemStack stack, @Nullable ClientLevel worldIn, @Nullable LivingEntity entityIn, int i) {
        if (modelOriginal instanceof FurnitureModel) {
            CompoundTag tag = stack.getTagElement("BlockEntityTag");
            if (tag != null) {
                FurnitureMaterial primary = InteriorRegistries.FURNITURE_MATERIALS.getValue(ResourceLocation.tryParse(tag.getString("primaryMaterial")));
                FurnitureMaterial secondary = InteriorRegistries.FURNITURE_MATERIALS.getValue(ResourceLocation.tryParse(tag.getString("secondaryMaterial")));
                return ((FurnitureModel) modelOriginal).getCustomModel(primary, secondary);
            }
        }
        return modelOriginal;
    }
}
