package com.example.inventoryhighlight;

public enum HighlightColor {
    YELLOW("Yellow",  0x80FFFF00),
    GREEN ("Green",   0x8000FF00),
    BLUE  ("Blue",    0x800080FF),
    RED   ("Red",     0x80FF3030),
    PURPLE("Purple",  0x80CC00FF),
    WHITE ("White",   0x80FFFFFF);

    public final String displayName;
    /** Packed ARGB color used by GuiGraphics.fill() */
    public final int argb;

    HighlightColor(String displayName, int argb) {
        this.displayName = displayName;
        this.argb = argb;
    }
}
