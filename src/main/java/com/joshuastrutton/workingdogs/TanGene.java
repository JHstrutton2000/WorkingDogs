package com.joshuastrutton.workingdogs;

public enum TanGene {
    LIGHT,
    NORMAL,
    RICH;

    public static TanGene byId(int id) {
        TanGene[] values = values();
        return values[Math.floorMod(id, values.length)];
    }
}
