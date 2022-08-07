package net.themcbrothers.interiormod.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.themcbrothers.interiormod.init.InteriorBlockEntityTypes;

/**
 * @author TheMCBrothers
 */
public class NightlightLampBlockEntity extends BlockEntity {

    public NightlightLampBlockEntity(BlockPos pos, BlockState state) {
        super(InteriorBlockEntityTypes.LAMP.get(), pos, state);
    }

}
