package com.joshuastrutton.workingdogs;

import com.joshuastrutton.workingdogs.registry.ModEntities;
import com.joshuastrutton.workingdogs.registry.ModItems;
import com.joshuastrutton.workingdogs.registry.ModBlocks;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(WorkingDogs.MOD_ID)
public final class WorkingDogs {
    public static final String MOD_ID = "workingdogs";

    public WorkingDogs(IEventBus modBus) {
        ModEntities.ENTITIES.register(modBus);
        ModBlocks.BLOCKS.register(modBus);
        ModItems.ITEMS.register(modBus);
        modBus.addListener(WorkingDogs::registerAttributes);
        modBus.addListener(WorkingDogs::registerSpawnPlacements);
        modBus.addListener(WorkingDogs::addCreativeTabItems);
        NeoForge.EVENT_BUS.addListener(WorkingDogCommands::register);
    }

    private static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.WORKING_DOG.get(), WorkingDog.createAttributes().build());
        event.put(ModEntities.ZOMBIE_DOG.get(), UndeadDog.createAttributes().build());
        event.put(ModEntities.SKELETON_DOG.get(), UndeadDog.createAttributes().build());
    }

    private static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(
                ModEntities.WORKING_DOG.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
    }

    private static void addCreativeTabItems(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(ModItems.WORKING_DOG_SPAWN_EGG.get());
            event.accept(ModItems.ZOMBIE_DOG_SPAWN_EGG.get());
            event.accept(ModItems.SKELETON_DOG_SPAWN_EGG.get());
            event.accept(ModItems.BLUE_HEELER_SPAWN_EGG.get());
            event.accept(ModItems.RED_HEELER_SPAWN_EGG.get());
            event.accept(ModItems.GERMAN_SHEPHERD_SPAWN_EGG.get());
            event.accept(ModItems.GOLDEN_RETRIEVER_SPAWN_EGG.get());
            event.accept(ModItems.LABRADOR_RETRIEVER_SPAWN_EGG.get());
            event.accept(ModItems.BORDER_COLLIE_SPAWN_EGG.get());
            event.accept(ModItems.CHIHUAHUA_SPAWN_EGG.get());
            event.accept(ModItems.FRENCH_BULLDOG_SPAWN_EGG.get());
            event.accept(ModItems.DACHSHUND_SPAWN_EGG.get());
            event.accept(ModItems.POODLE_SPAWN_EGG.get());
            event.accept(ModItems.BEAGLE_SPAWN_EGG.get());
            event.accept(ModItems.ROTTWEILER_SPAWN_EGG.get());
            event.accept(ModItems.GERMAN_SHORTHAIRED_POINTER_SPAWN_EGG.get());
            event.accept(ModItems.BULLDOG_SPAWN_EGG.get());
            event.accept(ModItems.CANE_CORSO_SPAWN_EGG.get());
            event.accept(ModItems.CAVALIER_SPAWN_EGG.get());
            event.accept(ModItems.YORKSHIRE_TERRIER_SPAWN_EGG.get());
            event.accept(ModItems.AUSTRALIAN_SHEPHERD_SPAWN_EGG.get());
            event.accept(ModItems.DOBERMAN_SPAWN_EGG.get());
            event.accept(ModItems.CORGI_SPAWN_EGG.get());
            event.accept(ModItems.MINIATURE_SCHNAUZER_SPAWN_EGG.get());
            event.accept(ModItems.BOXER_SPAWN_EGG.get());
            event.accept(ModItems.POMERANIAN_SPAWN_EGG.get());
            event.accept(ModItems.BERNESE_MOUNTAIN_DOG_SPAWN_EGG.get());
        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.BLUE_HEELER_FUR.get());
            event.accept(ModItems.RED_HEELER_FUR.get());
        }
        if (event.getTabKey() == CreativeModeTabs.COLORED_BLOCKS) {
            event.accept(ModItems.BLUE_HEELER_WOOL.get());
            event.accept(ModItems.RED_HEELER_WOOL.get());
        }
    }
}
