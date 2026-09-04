package com.joshuastrutton.workingdogs;

public enum WhiteMarkGene {
    NONE,
    BENTLEY,
    BLAZE;

    public static WhiteMarkGene byId(int id) {
        WhiteMarkGene[] values = values();
        return values[Math.floorMod(id, values.length)];
    }
}
