package net.themcbrothers.interiormod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.HitResult;
import net.themcbrothers.interiormod.api.InteriorAPI;
import net.themcbrothers.interiormod.api.furniture.FurnitureType;
import net.themcbrothers.interiormod.blockentity.ChairBlockEntity;
import net.themcbrothers.interiormod.blockentity.FurnitureBlockEntity;
import net.themcbrothers.interiormod.init.FurnitureMaterials;

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
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (stack.hasTag() && stack.getOrCreateTag().contains("BlockEntityTag", Tag.TAG_COMPOUND)) {
            CompoundTag tag = stack.getOrCreateTag().getCompound("BlockEntityTag");

            if (worldIn.getBlockEntity(pos) instanceof FurnitureBlockEntity furnitureTileEntity) {
                furnitureTileEntity.setPrimaryMaterial(
                        () -> InteriorAPI.furnitureRegistry().getValue(ResourceLocation.tryParse(tag.getString("primaryMaterial"))));
                furnitureTileEntity.setSecondaryMaterial(
                        () -> InteriorAPI.furnitureRegistry().getValue(ResourceLocation.tryParse(tag.getString("secondaryMaterial"))));

                // todo maybe move this?
                if (furnitureTileEntity instanceof ChairBlockEntity chairBlockEntity)
                    chairBlockEntity.setFacing(state.getValue(BlockStateProperties.HORIZONTAL_FACING));
            }
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        if (world.getBlockEntity(pos) instanceof FurnitureBlockEntity furnitureBlockEntity) {
            return FurnitureMaterials.createItemStack(this.furnitureType, furnitureBlockEntity.getPrimaryMaterial(),
                    furnitureBlockEntity.getSecondaryMaterial());
        }
        return super.getCloneItemStack(state, target, world, pos, player);
    }
}
