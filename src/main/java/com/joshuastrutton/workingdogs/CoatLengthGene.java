package com.joshuastrutton.workingdogs;

public enum CoatLengthGene {
    SHORT,
    LONG;

    public static CoatLengthGene byId(int id) {
        CoatLengthGene[] values = values();
        return values[Math.floorMod(id, values.length)];
    }
}
