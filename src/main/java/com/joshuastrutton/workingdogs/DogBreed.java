package com.joshuastrutton.workingdogs;

import java.util.Locale;

public enum DogBreed {
    AUSTRALIAN_CATTLE_DOG,
    GERMAN_SHEPHERD,
    GOLDEN_RETRIEVER,
    LABRADOR_RETRIEVER,
    BORDER_COLLIE,
    CHIHUAHUA,
    FRENCH_BULLDOG,
    DACHSHUND,
    POODLE,
    BEAGLE,
    ROTTWEILER,
    GERMAN_SHORTHAIRED_POINTER,
    BULLDOG,
    CANE_CORSO,
    CAVALIER_KING_CHARLES_SPANIEL,
    YORKSHIRE_TERRIER,
    AUSTRALIAN_SHEPHERD,
    DOBERMAN_PINSCHER,
    PEMBROKE_WELSH_CORGI,
    MINIATURE_SCHNAUZER,
    BOXER,
    POMERANIAN,
    BERNESE_MOUNTAIN_DOG;

    public String serializedName() {
        return name().toLowerCase(Locale.ROOT);
    }

    public static DogBreed byId(int id) {
        DogBreed[] values = values();
        return values[Math.floorMod(id, values.length)];
    }
}
