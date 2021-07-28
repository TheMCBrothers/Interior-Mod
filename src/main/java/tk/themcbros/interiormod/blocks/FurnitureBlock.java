package tk.themcbros.interiormod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.util.Constants;
import tk.themcbros.interiormod.api.furniture.FurnitureMaterial;
import tk.themcbros.interiormod.api.furniture.FurnitureType;
import tk.themcbros.interiormod.api.furniture.InteriorRegistries;
import tk.themcbros.interiormod.blockentity.ChairBlockEntity;
import tk.themcbros.interiormod.blockentity.FurnitureBlockEntity;
import tk.themcbros.interiormod.init.FurnitureMaterials;

import javax.annotation.Nullable;

public abstract class FurnitureBlock extends BaseEntityBlock {

    private final FurnitureType furnitureType;

    public FurnitureBlock(FurnitureType furnitureType, Properties properties) {
        super(properties);
        this.furnitureType = furnitureType;
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
        for (FurnitureMaterial material : InteriorRegistries.FURNITURE_MATERIALS) {
            items.add(FurnitureMaterials.createItemStack(this.furnitureType, material, material));
        }
    }

	@Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (stack.hasTag() && stack.getOrCreateTag().contains("textures", Constants.NBT.TAG_COMPOUND)) {
            CompoundTag tag = stack.getOrCreateTag().getCompound("textures");

            BlockEntity tile = worldIn.getBlockEntity(pos);

            if (tile instanceof FurnitureBlockEntity furnitureTileEntity) {
				furnitureTileEntity.setPrimaryMaterial(
                        () -> InteriorRegistries.FURNITURE_MATERIALS.getValue(ResourceLocation.tryParse(tag.getString("primary"))));
                furnitureTileEntity.setSecondaryMaterial(
                        () -> InteriorRegistries.FURNITURE_MATERIALS.getValue(ResourceLocation.tryParse(tag.getString("secondary"))));

                // todo maybe move this?
                if (furnitureTileEntity instanceof ChairBlockEntity chairBlockEntity)
                    chairBlockEntity.setFacing(state.getValue(BlockStateProperties.HORIZONTAL_FACING));
            }
        }
    }

	@Override
	public ItemStack getPickBlock(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof FurnitureBlockEntity furnitureBlockEntity) {
			return FurnitureMaterials.createItemStack(this.furnitureType, furnitureBlockEntity.getPrimaryMaterial(),
					furnitureBlockEntity.getSecondaryMaterial());
		}
		return super.getPickBlock(state, target, world, pos, player);
	}
}
