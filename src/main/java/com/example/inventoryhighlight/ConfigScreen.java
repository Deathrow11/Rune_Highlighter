package com.example.inventoryhighlight;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ConfigScreen extends Screen {

    private final Screen parent;
    private boolean draftEnabled;
    private HighlightColor draftColor;
    private HighlightMode draftMode;
    private String draftSearch;

    private Button enableButton;
    private Button colorButton;
    private Button modeButton;
    private EditBox searchBox;

    public ConfigScreen(Screen parent) {
        super(Component.translatable("config.inventoryhighlight.title"));
        this.parent = parent;
        this.draftEnabled = ModConfig.INSTANCE.enabled;
        this.draftColor = ModConfig.INSTANCE.highlightColor;
        this.draftMode = ModConfig.INSTANCE.highlightMode;
        this.draftSearch = ModConfig.INSTANCE.searchFilter;
    }

    @Override
    protected void init() {
        int centerX = width / 2;
        int y = height / 4 + 10;
        y += 30;

        // Toggle enable/disable
        enableButton = Button.builder(getEnableLabel(), button -> {
            draftEnabled = !draftEnabled;
            button.setMessage(getEnableLabel());
        }).bounds(centerX - 100, y, 200, 20).build();
        addRenderableWidget(enableButton);
        y += 30;

        // Cycle highlight color
        colorButton = Button.builder(getColorLabel(), button -> {
            HighlightColor[] colors = HighlightColor.values();
            int next = (draftColor.ordinal() + 1) % colors.length;
            draftColor = colors[next];
            button.setMessage(getColorLabel());
        }).bounds(centerX - 100, y, 200, 20).build();
        addRenderableWidget(colorButton);
        y += 30;

        // Cycle highlight mode
        modeButton = Button.builder(getModeLabel(), button -> {
            HighlightMode[] modes = HighlightMode.values();
            int next = (draftMode.ordinal() + 1) % modes.length;
            draftMode = modes[next];
            button.setMessage(getModeLabel());
        }).bounds(centerX - 100, y, 200, 20).build();
        addRenderableWidget(modeButton);
        y += 40;

        // Save / Cancel
        addRenderableWidget(Button.builder(
                Component.translatable("config.inventoryhighlight.save"),
                button -> save()).bounds(centerX - 100, y, 98, 20).build());

        addRenderableWidget(Button.builder(
                Component.translatable("config.inventoryhighlight.cancel"),
                button -> onClose()).bounds(centerX + 2, y, 98, 20).build());
    }

    private Component getEnableLabel() {
        return Component.translatable(
                draftEnabled
                        ? "config.inventoryhighlight.enable_on"
                        : "config.inventoryhighlight.enable_off");
    }

    private Component getColorLabel() {
        return Component.translatable("config.inventoryhighlight.color", draftColor.displayName);
    }

    private Component getModeLabel() {
        return Component.translatable("config.inventoryhighlight.mode", draftMode.displayName);
    }

    private void save() {
        ModConfig.INSTANCE.enabled = draftEnabled;
        ModConfig.INSTANCE.highlightColor = draftColor;
        ModConfig.INSTANCE.highlightMode = draftMode;
        ModConfig.INSTANCE.searchFilter = draftSearch.trim();
        ModConfig.save();

        if (minecraft != null && minecraft.player != null) {
            minecraft.player.displayClientMessage(
                    Component.translatable("config.inventoryhighlight.saved"),
                    false);
        }
        onClose();
    }

    @Override
    public void onClose() {
        if (minecraft != null) {
            minecraft.setScreen(parent);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        graphics.drawCenteredString(
                font,
                Component.translatable("config.inventoryhighlight.description"),
                width / 2,
                height / 4 - 12,
                0xA0A0A0);
    }
}