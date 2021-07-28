package tk.themcbros.interiormod.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import tk.themcbros.interiormod.init.InteriorBlockEntities;

/**
 * @author TheMCBrothers
 */
public class TableBlockEntity extends FurnitureBlockEntity {

    public TableBlockEntity(BlockPos pos, BlockState state) {
        super(InteriorBlockEntities.TABLE, pos, state);
    }

}
