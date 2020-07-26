package tk.themcbros.interiormod.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import tk.themcbros.interiormod.api.furniture.FurnitureMaterial;
import tk.themcbros.interiormod.api.furniture.FurnitureType;
import tk.themcbros.interiormod.api.furniture.InteriorRegistries;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author TheMCBrothers
 */
public class FurnitureBlockItem extends BlockItem {

    public FurnitureBlockItem(FurnitureType furniture, Properties builder) {
        super(furniture.getBlock(), builder);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (stack.hasTag() && stack.getOrCreateTag().contains("textures", Constants.NBT.TAG_COMPOUND)) {
            CompoundNBT tag = stack.getOrCreateTag().getCompound("textures");
            FurnitureMaterial primaryMaterial = InteriorRegistries.FURNITURE_MATERIALS.getValue(ResourceLocation.tryCreate(tag.getString("primary")));
            FurnitureMaterial secondaryMaterial = InteriorRegistries.FURNITURE_MATERIALS.getValue(ResourceLocation.tryCreate(tag.getString("secondary")));

            if (primaryMaterial != null) {
                ITextComponent primary = primaryMaterial.getDisplayName();
                primary.getStyle().applyFormatting(TextFormatting.GREEN);
                tooltip.add(primary);
            }
            if (secondaryMaterial != null && secondaryMaterial != primaryMaterial) {
                ITextComponent secondary = secondaryMaterial.getDisplayName();
                secondary.getStyle().applyFormatting(TextFormatting.GREEN);
                tooltip.add(secondary);
            }
        }
    }
}
