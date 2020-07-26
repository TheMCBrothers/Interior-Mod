package tk.themcbros.interiormod.client.models.block;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import tk.themcbros.interiormod.api.furniture.FurnitureMaterial;
import tk.themcbros.interiormod.api.furniture.InteriorRegistries;

import javax.annotation.Nullable;

/**
 * @author TheMCBrothers
 */
public class ChairItemOverride extends ItemOverrideList {
    @Nullable
    @Override
    public IBakedModel func_239290_a_(IBakedModel modelOriginal, ItemStack stack, @Nullable ClientWorld worldIn, @Nullable LivingEntity entityIn) {
        if(modelOriginal instanceof ChairModel) {
            CompoundNBT tag = stack.getChildTag("textures");
            if(tag != null) {
                FurnitureMaterial primary = InteriorRegistries.FURNITURE_MATERIALS.getValue(ResourceLocation.tryCreate(tag.getString("primary")));
                FurnitureMaterial secondary = InteriorRegistries.FURNITURE_MATERIALS.getValue(ResourceLocation.tryCreate(tag.getString("secondary")));
                return ((ChairModel) modelOriginal).getCustomModel(primary, secondary, Direction.NORTH);
            }
        }
        return modelOriginal;
    }
}
