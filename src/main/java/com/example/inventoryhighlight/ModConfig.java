package com.example.inventoryhighlight;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfig {

    public static ModConfig INSTANCE = new ModConfig();

    private static final Logger LOGGER = LoggerFactory.getLogger("inventoryhighlight");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH =
            FabricLoader.getInstance().getConfigDir().resolve("inventoryhighlight.json");

    // ── Configurable fields ──────────────────────────────────────────────────
    public boolean enabled = true;
    public HighlightColor highlightColor = HighlightColor.YELLOW;
    public String searchFilter = "";

    // ── Persistence ──────────────────────────────────────────────────────────
    public static void load() {
        if (!Files.exists(CONFIG_PATH)) {
            INSTANCE = new ModConfig();
            save();
            return;
        }
        try {
            String json = Files.readString(CONFIG_PATH);
            ModConfig loaded = GSON.fromJson(json, ModConfig.class);
            // Guard against null fields from an older config file
            if (loaded.highlightColor == null) loaded.highlightColor = HighlightColor.YELLOW;
            if (loaded.searchFilter == null) loaded.searchFilter = "";
            INSTANCE = loaded;
        } catch (IOException e) {
            LOGGER.error("[InventoryHighlight] Failed to load config, using defaults.", e);
            INSTANCE = new ModConfig();
        }
    }

    public static void save() {
        try {
            Files.writeString(CONFIG_PATH, GSON.toJson(INSTANCE));
        } catch (IOException e) {
            LOGGER.error("[InventoryHighlight] Failed to save config.", e);
        }
    }
}
