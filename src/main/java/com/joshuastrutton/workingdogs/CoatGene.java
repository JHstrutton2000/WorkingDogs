package com.joshuastrutton.workingdogs;

public enum CoatGene {
    DARK,
    LIGHT,
    RED;

    public static CoatGene byId(int id) {
        CoatGene[] values = values();
        return values[Math.floorMod(id, values.length)];
    }
}
