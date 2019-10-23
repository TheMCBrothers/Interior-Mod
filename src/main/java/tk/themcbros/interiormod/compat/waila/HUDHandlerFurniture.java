package tk.themcbros.interiormod.compat.waila;

import java.util.List;

import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;

public class HUDHandlerFurniture implements IComponentProvider {
	
	static final HUDHandlerFurniture INSTANCE = new HUDHandlerFurniture();
	
	@Override
	public void appendHead(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config) {
		tooltip.clear();
		final BlockState state = accessor.getBlockState();
		final RayTraceResult hitResult = accessor.getHitResult();
		final IBlockReader world = accessor.getWorld();
		final BlockPos pos = accessor.getPosition();
		final PlayerEntity player = accessor.getPlayer();
		final ItemStack stack = accessor.getBlock().getPickBlock(state, hitResult, world, pos, player);
		tooltip.add(stack.getDisplayName());
	}

}
