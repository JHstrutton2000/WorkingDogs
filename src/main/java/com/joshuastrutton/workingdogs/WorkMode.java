package com.joshuastrutton.workingdogs;

public enum WorkMode {
    COMPANION,
    HERD,
    RETRIEVE,
    GUARD;

    public WorkMode next() {
        return values()[(ordinal() + 1) % values().length];
    }

    public static WorkMode byId(int id) {
        WorkMode[] values = values();
        return values[Math.floorMod(id, values.length)];
    }
}
