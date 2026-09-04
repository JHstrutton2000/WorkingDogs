package com.joshuastrutton.workingdogs.registry;

import com.joshuastrutton.workingdogs.WorkingDogs;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, WorkingDogs.MOD_ID);

    public static final DeferredHolder<Item, SpawnEggItem> WORKING_DOG_SPAWN_EGG =
            ITEMS.register("working_dog_spawn_egg", () -> new SpawnEggItem(
                    ModEntities.WORKING_DOG.get(), 0x5B6670, 0xB33A2F, new Item.Properties()));

    public static final DeferredHolder<Item, SpawnEggItem> BLUE_HEELER_SPAWN_EGG =
            ITEMS.register("blue_heeler_spawn_egg", () -> new SpawnEggItem(
                    ModEntities.WORKING_DOG.get(), 0x52636C, 0x171B1D,
                    blueHeelerProperties()));
    public static final DeferredHolder<Item, SpawnEggItem> RED_HEELER_SPAWN_EGG =
            ITEMS.register("red_heeler_spawn_egg", () -> new SpawnEggItem(
                    ModEntities.WORKING_DOG.get(), 0xA84932, 0xE3D1B6,
                    redHeelerProperties()));
    public static final DeferredHolder<Item, SpawnEggItem> GERMAN_SHEPHERD_SPAWN_EGG = breedEgg("german_shepherd", "GERMAN_SHEPHERD", 0x9A6A35, 0x171717);
    public static final DeferredHolder<Item, SpawnEggItem> GOLDEN_RETRIEVER_SPAWN_EGG = breedEgg("golden_retriever", "GOLDEN_RETRIEVER", 0xD8A34E, 0xF0D28A);
    public static final DeferredHolder<Item, SpawnEggItem> LABRADOR_RETRIEVER_SPAWN_EGG = breedEgg("labrador_retriever", "LABRADOR_RETRIEVER", 0xD2B36F, 0x3A2C20);
    public static final DeferredHolder<Item, SpawnEggItem> BORDER_COLLIE_SPAWN_EGG = breedEgg("border_collie", "BORDER_COLLIE", 0x171717, 0xF0EEE8);

    public static final DeferredHolder<Item, SpawnEggItem> CHIHUAHUA_SPAWN_EGG =
            ITEMS.register("chihuahua_spawn_egg", () -> new SpawnEggItem(
                    ModEntities.WORKING_DOG.get(), 0xC9945A, 0x3A2418,
                    breedProperties("CHIHUAHUA")));

    public static final DeferredHolder<Item, SpawnEggItem> FRENCH_BULLDOG_SPAWN_EGG = breedEgg("french_bulldog", "FRENCH_BULLDOG", 0xB9A68C, 0x2A2725);
    public static final DeferredHolder<Item, SpawnEggItem> DACHSHUND_SPAWN_EGG = breedEgg("dachshund", "DACHSHUND", 0x8B4A24, 0x29150D);
    public static final DeferredHolder<Item, SpawnEggItem> POODLE_SPAWN_EGG = breedEgg("poodle", "POODLE", 0xE7DDCB, 0x9B7758);
    public static final DeferredHolder<Item, SpawnEggItem> BEAGLE_SPAWN_EGG = breedEgg("beagle", "BEAGLE", 0xB7773B, 0xF0E5CF);
    public static final DeferredHolder<Item, SpawnEggItem> ROTTWEILER_SPAWN_EGG = breedEgg("rottweiler", "ROTTWEILER", 0x171717, 0xA55A2A);
    public static final DeferredHolder<Item, SpawnEggItem> GERMAN_SHORTHAIRED_POINTER_SPAWN_EGG = breedEgg("german_shorthaired_pointer", "GERMAN_SHORTHAIRED_POINTER", 0x5A3828, 0xD8C6AA);
    public static final DeferredHolder<Item, SpawnEggItem> BULLDOG_SPAWN_EGG = breedEgg("bulldog", "BULLDOG", 0xC8B59A, 0x6B4932);
    public static final DeferredHolder<Item, SpawnEggItem> CANE_CORSO_SPAWN_EGG = breedEgg("cane_corso", "CANE_CORSO", 0x35383B, 0x121314);
    public static final DeferredHolder<Item, SpawnEggItem> CAVALIER_SPAWN_EGG = breedEgg("cavalier_king_charles_spaniel", "CAVALIER_KING_CHARLES_SPANIEL", 0x8C3F24, 0xF4E8D2);
    public static final DeferredHolder<Item, SpawnEggItem> YORKSHIRE_TERRIER_SPAWN_EGG = breedEgg("yorkshire_terrier", "YORKSHIRE_TERRIER", 0x8D7555, 0x293039);
    public static final DeferredHolder<Item, SpawnEggItem> AUSTRALIAN_SHEPHERD_SPAWN_EGG = breedEgg("australian_shepherd", "AUSTRALIAN_SHEPHERD", 0x6D625C, 0xD7C5A6);
    public static final DeferredHolder<Item, SpawnEggItem> DOBERMAN_SPAWN_EGG = breedEgg("doberman_pinscher", "DOBERMAN_PINSCHER", 0x171413, 0x9C512B);
    public static final DeferredHolder<Item, SpawnEggItem> CORGI_SPAWN_EGG = breedEgg("pembroke_welsh_corgi", "PEMBROKE_WELSH_CORGI", 0xC96F31, 0xF4E5C8);
    public static final DeferredHolder<Item, SpawnEggItem> MINIATURE_SCHNAUZER_SPAWN_EGG = breedEgg("miniature_schnauzer", "MINIATURE_SCHNAUZER", 0x777979, 0xD6D1C7);
    public static final DeferredHolder<Item, SpawnEggItem> BOXER_SPAWN_EGG = breedEgg("boxer", "BOXER", 0xB66B39, 0x24201E);
    public static final DeferredHolder<Item, SpawnEggItem> POMERANIAN_SPAWN_EGG = breedEgg("pomeranian", "POMERANIAN", 0xD8873F, 0xF0C98E);
    public static final DeferredHolder<Item, SpawnEggItem> BERNESE_MOUNTAIN_DOG_SPAWN_EGG = breedEgg("bernese_mountain_dog", "BERNESE_MOUNTAIN_DOG", 0x181818, 0xE7DED0);

    public static final DeferredHolder<Item, Item> BLUE_HEELER_FUR =
            ITEMS.register("blue_heeler_fur", () -> new Item(new Item.Properties()));

    public static final DeferredHolder<Item, Item> RED_HEELER_FUR =
            ITEMS.register("red_heeler_fur", () -> new Item(new Item.Properties()));

    public static final DeferredHolder<Item, BlockItem> BLUE_HEELER_WOOL =
            ITEMS.register("blue_heeler_wool", () -> new BlockItem(
                    ModBlocks.BLUE_HEELER_WOOL.get(), new Item.Properties()));

    public static final DeferredHolder<Item, BlockItem> RED_HEELER_WOOL =
            ITEMS.register("red_heeler_wool", () -> new BlockItem(
                    ModBlocks.RED_HEELER_WOOL.get(), new Item.Properties()));

    private static Item.Properties blueHeelerProperties() {
        CompoundTag entityData = new CompoundTag();
        entityData.putString("id", "workingdogs:working_dog");
        entityData.putString("DogBreed", "AUSTRALIAN_CATTLE_DOG");

        // One non-red allele guarantees a blue phenotype. The other color
        // allele and every marking locus remain randomized by finalizeSpawn.
        entityData.putString("MaternalCoatGene", "DARK");

        return new Item.Properties().component(
                DataComponents.ENTITY_DATA,
                CustomData.of(entityData));
    }

    private static Item.Properties redHeelerProperties() {
        CompoundTag entityData = new CompoundTag();
        entityData.putString("id", "workingdogs:working_dog");
        entityData.putString("DogBreed", "AUSTRALIAN_CATTLE_DOG");
        entityData.putString("MaternalCoatGene", "RED");
        entityData.putString("PaternalCoatGene", "RED");
        return new Item.Properties().component(DataComponents.ENTITY_DATA, CustomData.of(entityData));
    }

    private static Item.Properties breedProperties(String breed) {
        CompoundTag entityData = new CompoundTag();
        entityData.putString("id", "workingdogs:working_dog");
        entityData.putString("DogBreed", breed);
        return new Item.Properties().component(
                DataComponents.ENTITY_DATA,
                CustomData.of(entityData));
    }

    private static DeferredHolder<Item, SpawnEggItem> breedEgg(
            String name, String breed, int primary, int spots) {
        return ITEMS.register(name + "_spawn_egg", () -> new SpawnEggItem(
                ModEntities.WORKING_DOG.get(), primary, spots, breedProperties(breed)));
    }

    private ModItems() {}
}
