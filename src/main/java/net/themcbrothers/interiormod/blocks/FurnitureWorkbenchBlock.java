package net.themcbrothers.interiormod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.themcbrothers.interiormod.container.FurnitureWorkbenchMenu;
import net.themcbrothers.interiormod.init.InteriorStats;

import javax.annotation.Nullable;

/**
 * @author TheMCBrothers
 */
@SuppressWarnings("deprecation")
public class FurnitureWorkbenchBlock extends Block {
    private static final Component CONTAINER_TITLE = new TranslatableComponent("container.interiormod.furniture_crafting");

    public FurnitureWorkbenchBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            player.openMenu(state.getMenuProvider(level, pos));
            player.awardStat(InteriorStats.INTERACT_WITH_FURNITURE_WORKBENCH);
            return InteractionResult.CONSUME;
        }
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider((id, inventory, player) -> new FurnitureWorkbenchMenu(id, inventory, ContainerLevelAccess.create(level, pos)), CONTAINER_TITLE);
    }
}
