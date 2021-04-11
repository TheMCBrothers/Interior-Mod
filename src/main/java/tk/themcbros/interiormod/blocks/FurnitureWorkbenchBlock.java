package tk.themcbros.interiormod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import tk.themcbros.interiormod.container.FurnitureWorkbenchContainer;
import tk.themcbros.interiormod.init.InteriorStats;

/**
 * @author TheMCBrothers
 */
@SuppressWarnings("deprecation")
public class FurnitureWorkbenchBlock extends Block {
    private static final ITextComponent CONTAINER_NAME = new TranslationTextComponent("container.crafting");

    public FurnitureWorkbenchBlock(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            player.openContainer(state.getContainer(worldIn, pos));
            player.addStat(InteriorStats.INTERACT_WITH_FURNITURE_WORKBENCH);
            return ActionResultType.CONSUME;
        }
    }

    @Override
    public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
        return new SimpleNamedContainerProvider((id, inventory, player) ->
                new FurnitureWorkbenchContainer(id, inventory, IWorldPosCallable.of(worldIn, pos)), CONTAINER_NAME);
    }
}
