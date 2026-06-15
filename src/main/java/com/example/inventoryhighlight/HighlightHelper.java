package com.example.inventoryhighlight;

import net.minecraft.ChatFormatting;
import net.minecraft.world.item.ItemStack;

import java.util.Locale;
import java.util.Set;

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
        Set<String> names = namesForMode(ModConfig.INSTANCE.highlightMode);

        for (String rune : names) {
            if (!filter.isEmpty() && !rune.toLowerCase(Locale.ROOT).contains(filter)) {
                continue;
            }
            if (name.contains(rune)) {
                return true;
            }
        }
        return false;
    }

    private static Set<String> namesForMode(HighlightMode mode) {
        return switch (mode) {
            case STRICT    -> StrictHighlightNames.NAMES;
            case LOW_LEVEL -> LowLevelHighlightNames.NAMES;
            default        -> NormalHighlightNames.NAMES;
        };
    }
}