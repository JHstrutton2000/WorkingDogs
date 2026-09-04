package com.joshuastrutton.workingdogs;

public enum TickingGene {
    LIGHT,
    MEDIUM,
    HEAVY;

    public static TickingGene byId(int id) {
        TickingGene[] values = values();
        return values[Math.floorMod(id, values.length)];
    }
}
