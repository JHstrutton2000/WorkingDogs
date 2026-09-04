package com.joshuastrutton.workingdogs;

public enum PatchGene {
    NONE,
    SMALL,
    LARGE;

    public static PatchGene byId(int id) {
        PatchGene[] values = values();
        return values[Math.floorMod(id, values.length)];
    }
}
