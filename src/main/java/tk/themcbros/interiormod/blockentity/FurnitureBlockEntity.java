package tk.themcbros.interiormod.blockentity;

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
import tk.themcbros.interiormod.api.furniture.FurnitureMaterial;
import tk.themcbros.interiormod.api.furniture.InteriorRegistries;
import tk.themcbros.interiormod.init.FurnitureMaterials;

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
        return this.save(new CompoundTag());
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return new ClientboundBlockEntityDataPacket(this.worldPosition, 0, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        if (level != null) this.load(pkt.getTag());
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        compound.putString("primaryMaterial", String.valueOf(this.primaryMaterial.get().getRegistryName()));
        compound.putString("secondaryMaterial", String.valueOf(this.secondaryMaterial.get().getRegistryName()));
        return super.save(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.primaryMaterial =
                () -> InteriorRegistries.FURNITURE_MATERIALS.getValue(ResourceLocation.tryParse(compound.getString("primaryMaterial")));
        this.secondaryMaterial =
                () -> InteriorRegistries.FURNITURE_MATERIALS.getValue(ResourceLocation.tryParse(compound.getString("secondaryMaterial")));
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
