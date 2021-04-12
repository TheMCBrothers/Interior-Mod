package tk.themcbros.interiormod.items;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import tk.themcbros.interiormod.util.TooltipHelper;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author TheMCBrothers
 */
public class TooltipBlockItem extends BlockItem {

    public TooltipBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        TooltipHelper.addTooltip(stack, tooltip);
    }
}
