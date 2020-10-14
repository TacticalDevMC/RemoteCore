package com.github.tacticaldevmc.remotecore.utils;

public enum C {
    WHITE(0, "f"),
    ORANGE(1, "6"),
    MAGENTE(2, "5"),
    LICHT_BLUE(3, "b"),
    YELLOW(4, "e"),
    LIME(5, "a"),
    PINK(6, "d"),
    GRAY(7, "8"),
    LICHT_GRAY(8, "7"),
    CYAN(9, "3"),
    PURPLE(10, "5"),
    BLUE(11, "1"),
    BROWN(12, null),
    GREEN(13, "2"),
    RED(14, "c"),
    BLACK(15, "0");

    private final int Byte;
    private final String colorCode;

    C(int Byte, String colorCode) {
        this.Byte = Byte;
        this.colorCode = colorCode;
    }

    public String getColorCode() {
        return this.colorCode;
    }

    public int getByte() {
        return this.Byte;
    }
}