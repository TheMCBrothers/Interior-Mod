package tk.themcbros.interiormod.items;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.ForgeI18n;
import org.lwjgl.glfw.GLFW;
import tk.themcbros.interiormod.util.Styles;

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
                    components.add(new TranslationTextComponent(newKey).setStyle(Styles.GREEN));
                }
            }
            if (Screen.hasShiftDown()) {
                tooltip.addAll(components);
            } else {
                tooltip.add(new TranslationTextComponent("tooltip.interiormod.hold_shift"));
            }
        }
    }
}
