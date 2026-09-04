package com.joshuastrutton.workingdogs;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.Level;

public final class SkeletonDog extends UndeadDog {
    public SkeletonDog(EntityType<? extends Wolf> type, Level level) {
        super(type, level);
    }
}
