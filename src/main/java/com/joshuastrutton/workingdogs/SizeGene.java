package com.joshuastrutton.workingdogs;

public enum SizeGene {
    SMALL(.94F), STANDARD(1.0F), LARGE(1.06F);
    private final float scale;
    SizeGene(float scale) { this.scale = scale; }
    public float scale() { return scale; }
    public static SizeGene byId(int id) {
        SizeGene[] values = values();
        return values[Math.floorMod(id, values.length)];
    }
}
