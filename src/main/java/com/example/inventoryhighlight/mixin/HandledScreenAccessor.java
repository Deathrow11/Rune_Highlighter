package com.example.inventoryhighlight.mixin;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Exposes the screen background position fields.
 * Mojang mappings rename these from x/y (Yarn) to leftPos/topPos.
 */
@Mixin(AbstractContainerScreen.class)
public interface HandledScreenAccessor {

    @Accessor("leftPos")
    int getBackgroundX();

    @Accessor("topPos")
    int getBackgroundY();

    @Accessor("hoveredSlot")
    net.minecraft.world.inventory.Slot getHoveredSlot();
}
