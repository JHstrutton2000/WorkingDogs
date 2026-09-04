package com.joshuastrutton.workingdogs;

public enum ChihuahuaHeadGene {
    DEER, APPLE;
    public static ChihuahuaHeadGene byId(int id) {
        ChihuahuaHeadGene[] values = values();
        return values[Math.floorMod(id, values.length)];
    }
}
