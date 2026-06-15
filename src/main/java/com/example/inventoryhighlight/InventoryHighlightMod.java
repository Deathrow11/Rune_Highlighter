package com.example.inventoryhighlight;

import com.example.inventoryhighlight.mixin.HandledScreenAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.Slot;
import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InventoryHighlightMod implements ClientModInitializer {

    public static final String MOD_ID = "inventoryhighlight";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    /** Default H — press to toggle, hold Shift while pressing to open config */
    public static KeyMapping toggleKey;

    @Override
    public void onInitializeClient() {
        ModConfig.load();
        LOGGER.info("InventoryHighlight initialized (enabled={})", ModConfig.INSTANCE.enabled);

        toggleKey = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "key.inventoryhighlight.toggle",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_H,
                        KeyMapping.Category.register(
                                Identifier.fromNamespaceAndPath(MOD_ID, MOD_ID))
                )
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.consumeClick()) {
                if (client.hasShiftDown()) {
                    client.setScreen(new ConfigScreen(client.screen));
                    return;
                }

                ModConfig.INSTANCE.enabled = !ModConfig.INSTANCE.enabled;
                ModConfig.save();

                if (client.player != null) {
                    client.player.displayClientMessage(
                            Component.translatable(
                                    ModConfig.INSTANCE.enabled
                                            ? "message.inventoryhighlight.enabled"
                                            : "message.inventoryhighlight.disabled"),
                            false);
                }
            }
        });

        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (!(screen instanceof AbstractContainerScreen<?> containerScreen)) {
                return;
            }

            ScreenEvents.afterRender(screen).register((s, graphics, mouseX, mouseY, tickDelta) -> {
                if (!ModConfig.INSTANCE.enabled) {
                    return;
                }

                HandledScreenAccessor accessor = (HandledScreenAccessor) s;
                int bgX = accessor.getBackgroundX();
                int bgY = accessor.getBackgroundY();

                for (Slot slot : containerScreen.getMenu().slots) {
                    if (!slot.hasItem() || !HighlightHelper.shouldHighlight(slot.getItem())) {
                        continue;
                    }

                    int sx = bgX + slot.x;
                    int sy = bgY + slot.y;
                    graphics.fill(sx, sy, sx + 16, sy + 16, ModConfig.INSTANCE.highlightColor.argb);
                }
            });
        });
    }
}
