package com.joshuastrutton.workingdogs;

public enum ChihuahuaColorGene {
    WHITE, CREAM, FAWN, RED, CHOCOLATE, BLUE, BLACK;
    public static ChihuahuaColorGene byId(int id) {
        ChihuahuaColorGene[] values = values();
        return values[Math.floorMod(id, values.length)];
    }
}
