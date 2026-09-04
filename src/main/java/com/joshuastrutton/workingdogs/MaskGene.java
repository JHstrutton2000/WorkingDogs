package com.joshuastrutton.workingdogs;

public enum MaskGene {
    NONE,
    LEFT,
    RIGHT,
    BOTH;

    public static MaskGene byId(int id) {
        MaskGene[] values = values();
        return values[Math.floorMod(id, values.length)];
    }
}
