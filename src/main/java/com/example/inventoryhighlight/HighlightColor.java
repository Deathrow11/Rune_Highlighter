package com.example.inventoryhighlight;

public enum HighlightColor {
    YELLOW  ("Yellow",  0x80FFFF00),
    RED     ("Red",     0x80FF4444),
    GREEN   ("Green",   0x8044FF44),
    BLUE    ("Blue",    0x804444FF),
    CYAN    ("Cyan",    0x8000FFFF),
    PURPLE  ("Purple",  0x80CC44FF),
    ORANGE  ("Orange",  0x80FF8800),
    WHITE   ("White",   0x80FFFFFF),
    PINK    ("Pink",    0x80FF88CC);

    public final String displayName;
    public final int argb;

    HighlightColor(String displayName, int argb) {
        this.displayName = displayName;
        this.argb = argb;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
