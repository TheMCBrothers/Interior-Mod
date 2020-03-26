package tk.themcbros.interiormod.blocks;

import net.minecraft.block.DoorBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.util.List;

public class ModernDoorBlock extends DoorBlock {

    public ModernDoorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flag) {
        ITextComponent component;
        if (GLFW.glfwGetKey(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_TRUE) {
            component = new StringTextComponent("This is a modern variant of the vanilla door").applyTextStyle(TextFormatting.GREEN);
        } else {
            component = new StringTextComponent("Press ").applyTextStyle(TextFormatting.GRAY)
                    .appendSibling(new StringTextComponent("Shift").applyTextStyles(TextFormatting.YELLOW, TextFormatting.ITALIC))
                    .appendSibling(new StringTextComponent(" for more information").applyTextStyle(TextFormatting.GRAY));
        }
        tooltip.add(component);
    }
}
