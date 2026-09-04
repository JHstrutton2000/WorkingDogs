package com.joshuastrutton.workingdogs;

public enum ChihuahuaPatternGene {
    SOLID, TAN_POINTS, SABLE, BRINDLE, MERLE;
    public static ChihuahuaPatternGene byId(int id) {
        ChihuahuaPatternGene[] values = values();
        return values[Math.floorMod(id, values.length)];
    }
}
