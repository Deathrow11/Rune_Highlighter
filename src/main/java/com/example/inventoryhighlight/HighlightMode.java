package com.example.inventoryhighlight;

public enum HighlightMode {
    NORMAL("Normal"),
    STRICT("Strict"),
    LOW_LEVEL("Low Level");

    public final String displayName;

    HighlightMode(String displayName) {
        this.displayName = displayName;
    }
}