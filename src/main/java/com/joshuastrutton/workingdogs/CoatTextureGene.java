package com.joshuastrutton.workingdogs;

public enum CoatTextureGene {
    STRAIGHT, WAVY, CURLY, WIRE;
    public static CoatTextureGene byId(int id) {
        CoatTextureGene[] values = values();
        return values[Math.floorMod(id, values.length)];
    }
}
