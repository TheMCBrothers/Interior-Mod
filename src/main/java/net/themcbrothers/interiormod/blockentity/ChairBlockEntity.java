package net.themcbrothers.interiormod.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import net.themcbrothers.interiormod.init.InteriorBlockEntityTypes;

import javax.annotation.Nonnull;

public class ChairBlockEntity extends FurnitureBlockEntity {
    public static ModelProperty<Direction> FACING = new ModelProperty<>();

    private Direction facing = Direction.NORTH;

    public ChairBlockEntity(BlockPos pos, BlockState state) {
        super(InteriorBlockEntityTypes.CHAIR.get(), pos, state);
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.putString("facing", this.facing != null ? this.facing.getSerializedName() : "north");
    }

    @Override
    public void load(CompoundTag compound) {
        this.facing = Direction.byName(compound.getString("facing"));
        super.load(compound);
    }

    @Nonnull
    @Override
    public ModelData getModelData() {
        return ModelData.builder().with(PRIMARY_MATERIAL, this.getPrimaryMaterial())
                .with(SECONDARY_MATERIAL, this.getSecondaryMaterial()).with(FACING, facing).build();
    }

    public Direction getFacing() {
        return facing;
    }

    public void setFacing(Direction direction) {
        this.facing = direction;
        if (this.level != null && this.level.isClientSide) {
            this.requestModelDataUpdate();
            this.level.sendBlockUpdated(this.worldPosition, this.level.getBlockState(this.worldPosition), this.level.getBlockState(this.worldPosition), 3);
        }
    }

}
