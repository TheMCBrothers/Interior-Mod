package tk.themcbros.interiormod.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import tk.themcbros.interiormod.init.InteriorBlockEntities;

import javax.annotation.Nonnull;

public class ChairBlockEntity extends FurnitureBlockEntity {

    public static ModelProperty<Direction> FACING = new ModelProperty<>();

    private Direction facing = Direction.NORTH;

    public ChairBlockEntity(BlockPos pos, BlockState state) {
        super(InteriorBlockEntities.CHAIR, pos, state);
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        compound.putString("facing", this.facing != null ? this.facing.getSerializedName() : "north");
        return super.save(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        this.facing = Direction.byName(compound.getString("facing"));
        super.load(compound);
    }

    @Nonnull
    @Override
    public IModelData getModelData() {
        return new ModelDataMap.Builder().withInitial(PRIMARY_MATERIAL, this.getPrimaryMaterial()).withInitial(SECONDARY_MATERIAL, this.getSecondaryMaterial()).withInitial(FACING, facing).build();
    }

    public Direction getFacing() {
        return facing;
    }

    public void setFacing(Direction direction) {
        this.facing = direction;
        if (this.level != null && this.level.isClientSide) {
            ModelDataManager.requestModelDataRefresh(this);
            this.level.sendBlockUpdated(this.worldPosition, this.level.getBlockState(this.worldPosition), this.level.getBlockState(this.worldPosition), 3);
        }
    }

}
