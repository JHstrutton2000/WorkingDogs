# Working Dogs

A NeoForge 1.21.1 mod that adds tameable, breedable working dogs.

## Included breeds

- Australian Cattle Dog (blue heeler and recessive red heeler coats)
- German Shepherd
- Golden Retriever
- Labrador Retriever (black, yellow, and chocolate coats)
- Border Collie

Blue and red heelers are represented correctly as coat variants of the same breed.

## Current playable milestone

- A custom wolf-derived dog entity and spawn egg
- Taming, sitting, following, combat, and breeding inherited from wolves
- Two inherited coat alleles saved to NBT and synchronized to clients
- Puppy genes randomly inherited one from each parent
- Companion, Herd, Retrieve, and Guard work modes
- Sneak is not required: right-click a tamed dog with a stick to cycle work modes
- Placeholder textures selected from Minecraft's wolf variants

The work modes and breed data are in place. Dedicated herding and retrieving AI,
breed-specific Blockbench models, custom textures, natural breed distribution,
and breed-selection items are the next implementation milestones.

## Run it

Install JDK 21, open the folder as a Gradle project, and run:

```text
./gradlew runClient
```

Build a distributable JAR with:

```text
./gradlew build
```

The JAR will be placed in `build/libs`.

## Developer notes

The primary extension points are:

- `DogBreed`: breed identity
- `CoatGene`: inherited alleles
- `WorkingDog#getCoatVariant`: genotype-to-visible-coat mapping
- `WorkMode`: selected job
- `WorkingDogRenderer`: temporary texture mapping

For custom models, make one shared canine rig in Blockbench, then export one model
per body shape while keeping animation bone names consistent. This permits shared
walk, sit, shake, sleep, herd, and retrieve animations across breeds.
