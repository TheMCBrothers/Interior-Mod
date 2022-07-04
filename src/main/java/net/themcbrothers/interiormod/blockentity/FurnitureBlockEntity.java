package net.themcbrothers.interiormod.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import net.themcbrothers.interiormod.api.InteriorAPI;
import net.themcbrothers.interiormod.api.furniture.FurnitureMaterial;
import net.themcbrothers.interiormod.init.FurnitureMaterials;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * @author TheMCBrothers
 */
public abstract class FurnitureBlockEntity extends BlockEntity {

    public static ModelProperty<FurnitureMaterial> PRIMARY_MATERIAL = new ModelProperty<>();
    public static ModelProperty<FurnitureMaterial> SECONDARY_MATERIAL = new ModelProperty<>();

    private Supplier<FurnitureMaterial> primaryMaterial = FurnitureMaterials.OAK_PLANKS;
    private Supplier<FurnitureMaterial> secondaryMaterial = FurnitureMaterials.OAK_PLANKS;

    public FurnitureBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }


    @Nonnull
    @Override
    public IModelData getModelData() {
        return new ModelDataMap.Builder().withInitial(PRIMARY_MATERIAL, primaryMaterial.get()).withInitial(SECONDARY_MATERIAL, secondaryMaterial.get())
                .build();
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag);
        return tag;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        if (pkt.getTag() != null)
            this.load(pkt.getTag());
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        compound.putString("primaryMaterial", String.valueOf(this.primaryMaterial.get().getRegistryName()));
        compound.putString("secondaryMaterial", String.valueOf(this.secondaryMaterial.get().getRegistryName()));
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.primaryMaterial =
                () -> InteriorAPI.furnitureRegistry().getValue(ResourceLocation.tryParse(compound.getString("primaryMaterial")));
        this.secondaryMaterial =
                () -> InteriorAPI.furnitureRegistry().getValue(ResourceLocation.tryParse(compound.getString("secondaryMaterial")));
    }

    public void setPrimaryMaterial(Supplier<FurnitureMaterial> primaryMaterial) {
        this.primaryMaterial = primaryMaterial;
        if (this.level != null && this.level.isClientSide) {
            ModelDataManager.requestModelDataRefresh(this);
            this.level.sendBlockUpdated(this.worldPosition, this.level.getBlockState(this.worldPosition), this.level.getBlockState(this.worldPosition), 3);
        }
    }

    public void setSecondaryMaterial(Supplier<FurnitureMaterial> secondaryMaterial) {
        this.secondaryMaterial = secondaryMaterial;
        if (this.level != null && this.level.isClientSide) {
            ModelDataManager.requestModelDataRefresh(this);
            this.level.sendBlockUpdated(this.worldPosition, this.level.getBlockState(this.worldPosition), this.level.getBlockState(this.worldPosition), 3);
        }
    }

    public FurnitureMaterial getPrimaryMaterial() {
        return this.primaryMaterial.get();
    }

    public FurnitureMaterial getSecondaryMaterial() {
        return this.secondaryMaterial.get();
    }
}
