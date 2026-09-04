package com.joshuastrutton.workingdogs;

public enum ChihuahuaWhiteGene {
    NONE, CHEST, IRISH, PIEBALD;
    public static ChihuahuaWhiteGene byId(int id) {
        ChihuahuaWhiteGene[] values = values();
        return values[Math.floorMod(id, values.length)];
    }
}
