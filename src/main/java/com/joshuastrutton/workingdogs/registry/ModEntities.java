package com.joshuastrutton.workingdogs.registry;

import com.joshuastrutton.workingdogs.WorkingDog;
import com.joshuastrutton.workingdogs.WorkingDogs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, WorkingDogs.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<WorkingDog>> WORKING_DOG =
            ENTITIES.register("working_dog", () -> EntityType.Builder
                    .of(WorkingDog::new, MobCategory.CREATURE)
                    .sized(0.65F, 0.9F)
                    .clientTrackingRange(10)
                    .build("working_dog"));

    private ModEntities() {}
}
