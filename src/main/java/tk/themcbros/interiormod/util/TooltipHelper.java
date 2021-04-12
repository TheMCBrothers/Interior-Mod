package tk.themcbros.interiormod.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.ForgeI18n;

import java.util.List;

public class TooltipHelper {

    public static void addTooltip(ItemStack stack, List<ITextComponent> tooltip) {
        String key = stack.getTranslationKey() + ".tooltip";
        String translated = ForgeI18n.getPattern(key);
        if (!key.equals(translated)) {
            for (String string : translated.split("\n")) {
                tooltip.add(new StringTextComponent(string).mergeStyle(TextFormatting.GOLD));
            }
        }
    }

}
