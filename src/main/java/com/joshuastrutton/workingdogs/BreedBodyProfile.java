package com.joshuastrutton.workingdogs;

/** Shared-rig proportions. Dedicated Blockbench rigs can later replace profiles independently. */
public record BreedBodyProfile(float scale, float headWidth, float headDepth,
                               float bodyWidth, float bodyDepth, float legLength,
                               float earSize, float tailThickness, boolean fullCoat) {
    public static BreedBodyProfile of(DogBreed breed, boolean longHair) {
        return switch (breed) {
            case CHIHUAHUA -> new BreedBodyProfile(.58F, 1.18F, .78F, .76F, .82F, .88F, 1.38F, longHair ? 1.45F : .68F, longHair);
            case FRENCH_BULLDOG -> new BreedBodyProfile(.68F, 1.24F, .68F, 1.18F, 1.08F, .62F, 1.20F, .48F, false);
            case DACHSHUND -> new BreedBodyProfile(.58F, .92F, 1.05F, .82F, 1.36F, .48F, .82F, .70F, longHair);
            case POODLE -> new BreedBodyProfile(.82F, .96F, .90F, .92F, .94F, 1.12F, .72F, 1.10F, true);
            case BEAGLE -> new BreedBodyProfile(.74F, 1.02F, 1.05F, .96F, 1.02F, .86F, .72F, .85F, false);
            case ROTTWEILER -> new BreedBodyProfile(.94F, 1.12F, .90F, 1.16F, 1.10F, .88F, .78F, .75F, false);
            case GERMAN_SHORTHAIRED_POINTER -> new BreedBodyProfile(.94F, .92F, 1.12F, .84F, 1.06F, 1.12F, .72F, .78F, false);
            case BULLDOG -> new BreedBodyProfile(.72F, 1.30F, .62F, 1.24F, 1.08F, .56F, .72F, .48F, false);
            case CANE_CORSO -> new BreedBodyProfile(1.06F, 1.16F, .82F, 1.22F, 1.14F, .96F, .72F, .72F, false);
            case CAVALIER_KING_CHARLES_SPANIEL -> new BreedBodyProfile(.62F, 1.10F, .84F, .92F, .94F, .76F, .76F, 1.10F, true);
            case YORKSHIRE_TERRIER -> new BreedBodyProfile(.52F, .96F, .86F, .74F, .88F, .82F, 1.02F, 1.05F, true);
            case AUSTRALIAN_SHEPHERD -> new BreedBodyProfile(.86F, 1.02F, .94F, 1.04F, 1.04F, .92F, .82F, 1.12F, true);
            case DOBERMAN_PINSCHER -> new BreedBodyProfile(.96F, .88F, 1.08F, .82F, 1.02F, 1.16F, 1.02F, .52F, false);
            case PEMBROKE_WELSH_CORGI -> new BreedBodyProfile(.68F, 1.04F, .94F, 1.00F, 1.25F, .48F, 1.12F, .35F, false);
            case MINIATURE_SCHNAUZER -> new BreedBodyProfile(.64F, 1.02F, 1.02F, .88F, .96F, .78F, .92F, .75F, true);
            case BOXER -> new BreedBodyProfile(.88F, 1.16F, .72F, 1.08F, 1.04F, .98F, .68F, .45F, false);
            case POMERANIAN -> new BreedBodyProfile(.58F, 1.10F, .82F, 1.18F, 1.12F, .72F, .92F, 1.48F, true);
            case BERNESE_MOUNTAIN_DOG -> new BreedBodyProfile(1.08F, 1.08F, .96F, 1.22F, 1.16F, .96F, .78F, 1.25F, true);
            case GOLDEN_RETRIEVER -> new BreedBodyProfile(.98F, 1.02F, .98F, 1.08F, 1.08F, .98F, .78F, 1.20F, true);
            case LABRADOR_RETRIEVER -> new BreedBodyProfile(.98F, 1.04F, .96F, 1.10F, 1.06F, .98F, .78F, 1.00F, false);
            case GERMAN_SHEPHERD -> new BreedBodyProfile(1.02F, 1.00F, 1.02F, 1.04F, 1.08F, 1.06F, 1.04F, 1.02F, false);
            case AUSTRALIAN_CATTLE_DOG -> new BreedBodyProfile(.88F, 1.02F, .96F, 1.04F, 1.02F, .88F, 1.02F, .92F, false);
            case BORDER_COLLIE -> new BreedBodyProfile(.88F, 1.00F, .98F, .96F, 1.02F, .94F, .92F, 1.15F, true);
        };
    }
}
