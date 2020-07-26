package tk.themcbros.interiormod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import tk.themcbros.interiormod.api.furniture.FurnitureMaterial;
import tk.themcbros.interiormod.api.furniture.FurnitureType;
import tk.themcbros.interiormod.api.furniture.InteriorRegistries;
import tk.themcbros.interiormod.init.FurnitureMaterials;
import tk.themcbros.interiormod.tileentity.ChairTileEntity;
import tk.themcbros.interiormod.tileentity.FurnitureTileEntity;

import javax.annotation.Nullable;

public abstract class FurnitureBlock extends Block {

	private final FurnitureType furnitureType;

	public FurnitureBlock(FurnitureType furnitureType, Properties properties) {
		super(properties);
		this.furnitureType = furnitureType;
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public abstract TileEntity createTileEntity(BlockState state, IBlockReader world);

	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		for (FurnitureMaterial material : InteriorRegistries.FURNITURE_MATERIALS) {
			items.add(FurnitureMaterials.createItemStack(this.furnitureType, material, material));
		}
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		if (stack.hasTag() && stack.getOrCreateTag().contains("textures", Constants.NBT.TAG_COMPOUND)) {
			CompoundNBT tag = stack.getOrCreateTag().getCompound("textures");

			TileEntity tile = worldIn.getTileEntity(pos);

			if (tile instanceof FurnitureTileEntity) {
				FurnitureTileEntity furnitureTileEntity = (FurnitureTileEntity) tile;
				furnitureTileEntity.setPrimaryMaterial(
						() -> InteriorRegistries.FURNITURE_MATERIALS.getValue(ResourceLocation.tryCreate(tag.getString("primary"))));
				furnitureTileEntity.setSecondaryMaterial(
						() -> InteriorRegistries.FURNITURE_MATERIALS.getValue(ResourceLocation.tryCreate(tag.getString("secondary"))));

				// todo maybe move this?
				if (furnitureTileEntity instanceof ChairTileEntity)
					((ChairTileEntity) furnitureTileEntity).setFacing(state.get(BlockStateProperties.HORIZONTAL_FACING));
			}
		}
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
		TileEntity tileEntity = world.getTileEntity(pos);
		if(tileEntity instanceof FurnitureTileEntity) {
			FurnitureTileEntity furnitureTileEntity = (FurnitureTileEntity) tileEntity;
			return FurnitureMaterials.createItemStack(this.furnitureType, furnitureTileEntity.getPrimaryMaterial(),
					furnitureTileEntity.getSecondaryMaterial());
		}
		return super.getPickBlock(state, target, world, pos, player);
	}
}
