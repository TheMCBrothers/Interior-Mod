package net.themcbrothers.interiormod.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeI18n;

import java.util.List;

public class TooltipHelper {

    public static void addTooltip(ItemStack stack, List<Component> tooltip) {
        String key = stack.getDescriptionId() + ".tooltip";
        String translated = ForgeI18n.getPattern(key);
        if (!key.equals(translated)) {
            for (String string : translated.split("\n")) {
                tooltip.add(new TextComponent(string).withStyle(ChatFormatting.GOLD));
            }
        }
    }

}
