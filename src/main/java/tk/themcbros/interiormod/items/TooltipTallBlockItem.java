package tk.themcbros.interiormod.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;

/**
 * @author TheMCBrothers
 */
public class TooltipTallBlockItem extends TooltipBlockItem {

    public TooltipTallBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    protected boolean placeBlock(BlockItemUseContext context, BlockState state) {
        context.getWorld().setBlockState(context.getPos().up(), Blocks.AIR.getDefaultState(), 27);
        return super.placeBlock(context, state);
    }
}
