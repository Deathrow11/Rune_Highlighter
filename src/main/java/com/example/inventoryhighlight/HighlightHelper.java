package com.example.inventoryhighlight;

import net.minecraft.ChatFormatting;
import net.minecraft.world.item.ItemStack;

import java.util.Locale;

public final class HighlightHelper {

    private HighlightHelper() {}

    public static boolean shouldHighlight(ItemStack stack) {
        if (!ModConfig.INSTANCE.enabled || stack.isEmpty()) {
            return false;
        }

        String name = ChatFormatting.stripFormatting(stack.getHoverName().getString())
                .trim()
                .toUpperCase(Locale.ROOT);
        if (name.isEmpty()) {
            return false;
        }

        String filter = ModConfig.INSTANCE.searchFilter.trim().toLowerCase(Locale.ROOT);

        for (String rune : HighlightNames.NAMES) {
            if (!filter.isEmpty() && !rune.toLowerCase(Locale.ROOT).contains(filter)) {
                continue;
            }
            if (name.contains(rune)) {
                return true;
            }
        }
        return false;
    }
}
