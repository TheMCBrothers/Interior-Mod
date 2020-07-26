package tk.themcbros.interiormod.items;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.ForgeI18n;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author TheMCBrothers
 */
public class DescBlockItem extends BlockItem {

    public DescBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        List<ITextComponent> components = Lists.newArrayList();
        String key = stack.getTranslationKey() + ".desc";

        if (I18n.hasKey(key + "0")) {
            for (int i = 0; i < 10; i++) {
                String newKey = key + i;
                if (I18n.hasKey(newKey)) {
                    ITextComponent component = new TranslationTextComponent(newKey);
                    component.getStyle().applyFormatting(TextFormatting.GREEN);
                    components.add(component);
                }
            }
            if (Screen.hasShiftDown()) {
                tooltip.addAll(components);
            } else {
                ITextComponent component = new TranslationTextComponent("tooltip.interiormod.hold_shift");
                tooltip.add(component);
            }
        }
    }
}
