package tk.themcbros.interiormod.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import tk.themcbros.interiormod.init.InteriorBlockEntities;

/**
 * @author TheMCBrothers
 */
public class NightlightLampBlockEntity extends BlockEntity {

    public NightlightLampBlockEntity(BlockPos pos, BlockState state) {
        super(InteriorBlockEntities.LAMP, pos, state);
    }

}
