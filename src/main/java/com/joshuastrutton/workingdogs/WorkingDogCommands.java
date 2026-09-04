package com.joshuastrutton.workingdogs;

import com.joshuastrutton.workingdogs.registry.ModEntities;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public final class WorkingDogCommands {
    private static final int GRID_COLUMNS = 24;
    private static final double GRID_SPACING = 2.5D;

    public static void register(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("workingdogs")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("testbreed")
                        .then(Commands.argument("breed", StringArgumentType.word())
                                .suggests((context, builder) -> SharedSuggestionProvider.suggest(
                                        Arrays.stream(DogBreed.values()).map(DogBreed::serializedName), builder))
                                .executes(WorkingDogCommands::spawnBreedTestGrid))));
    }

    private static int spawnBreedTestGrid(CommandContext<CommandSourceStack> context) {
        String requested = StringArgumentType.getString(context, "breed");
        DogBreed breed;
        try {
            breed = DogBreed.valueOf(requested.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            context.getSource().sendFailure(Component.literal("Unknown dog breed: " + requested));
            return 0;
        }

        ServerPlayer player;
        try {
            player = context.getSource().getPlayerOrException();
        } catch (com.mojang.brigadier.exceptions.CommandSyntaxException exception) {
            context.getSource().sendFailure(Component.literal("Run this command as a player."));
            return 0;
        }

        ServerLevel level = player.serverLevel();
        Map<String, Variation> variations = breed == DogBreed.AUSTRALIAN_CATTLE_DOG
                ? buildHeelerVariations(level, breed)
                : buildGeneralVariations(level, breed);

        int index = 0;
        for (Variation variation : variations.values()) {
            WorkingDog dog = variation.dog();
            int column = index % GRID_COLUMNS;
            int row = index / GRID_COLUMNS;
            double x = player.getX() + 3.0D + column * GRID_SPACING;
            double z = player.getZ() + 3.0D + row * GRID_SPACING;
            dog.moveTo(x, player.getY(), z, 180.0F, 0.0F);
            dog.setCustomName(Component.literal(variation.name()));
            dog.setCustomNameVisible(true);
            dog.setNoAi(true);
            dog.setInvulnerable(true);
            dog.setPersistenceRequired();
            level.addFreshEntity(dog);
            index++;
        }

        context.getSource().sendSuccess(() -> Component.literal(
                "Spawned " + variations.size() + " labeled " + pretty(breed)
                        + " texture/genetics variations."), true);
        return variations.size();
    }

    private static Map<String, Variation> buildGeneralVariations(ServerLevel level, DogBreed breed) {
        Map<String, Variation> result = new LinkedHashMap<>();
        for (ChihuahuaColorGene color : ChihuahuaColorGene.values()) {
            for (ChihuahuaPatternGene pattern : ChihuahuaPatternGene.values()) {
                for (ChihuahuaWhiteGene white : ChihuahuaWhiteGene.values()) {
                    for (CoatLengthGene length : CoatLengthGene.values()) {
                        WorkingDog dog = create(level, breed);
                        dog.setChihuahuaGenes(color, color, pattern, pattern, white, white,
                                ChihuahuaHeadGene.APPLE, ChihuahuaHeadGene.APPLE);
                        dog.setCoatLengthGenes(length, length);
                        dog.setStructureGenes(CoatTextureGene.STRAIGHT, CoatTextureGene.STRAIGHT,
                                SizeGene.STANDARD, SizeGene.STANDARD);
                        ChihuahuaPhenotype shown = dog.getChihuahuaPhenotype();
                        String key = shown.color() + "|" + shown.pattern() + "|" + shown.white()
                                + "|" + shown.longHaired();
                        String name = pretty(breed) + " | " + shown.color() + " | " + shown.pattern()
                                + " | " + shown.white() + " | "
                                + (shown.longHaired() ? "LONG" : "SHORT");
                        result.putIfAbsent(key, new Variation(dog, name));
                    }
                }
            }
        }

        for (CoatTextureGene texture : CoatTextureGene.values()) {
            WorkingDog dog = create(level, breed);
            dog.setStructureGenes(texture, texture, SizeGene.STANDARD, SizeGene.STANDARD);
            add(result, "texture:" + texture, dog, pretty(breed) + " | TEXTURE " + dog.getCoatTexture());
        }
        for (SizeGene size : SizeGene.values()) {
            WorkingDog dog = create(level, breed);
            dog.setStructureGenes(CoatTextureGene.STRAIGHT, CoatTextureGene.STRAIGHT, size, size);
            add(result, "size:" + size, dog, pretty(breed) + " | SIZE " + size);
        }
        if (breed == DogBreed.CHIHUAHUA) {
            for (ChihuahuaHeadGene head : ChihuahuaHeadGene.values()) {
                WorkingDog dog = create(level, breed);
                dog.setChihuahuaGenes(ChihuahuaColorGene.FAWN, ChihuahuaColorGene.FAWN,
                        ChihuahuaPatternGene.SOLID, ChihuahuaPatternGene.SOLID,
                        ChihuahuaWhiteGene.NONE, ChihuahuaWhiteGene.NONE, head, head);
                add(result, "head:" + head, dog, "Chihuahua | HEAD " + head);
            }
        }
        return result;
    }

    private static Map<String, Variation> buildHeelerVariations(ServerLevel level, DogBreed breed) {
        Map<String, Variation> result = new LinkedHashMap<>();
        for (CoatVariant color : new CoatVariant[]{CoatVariant.BLUE_HEELER, CoatVariant.RED_HEELER}) {
            for (MaskGene mask : MaskGene.values()) {
                for (TickingGene ticking : TickingGene.values()) {
                    for (PatchGene patch : PatchGene.values()) {
                        for (WhiteMarkGene white : WhiteMarkGene.values()) {
                            for (TanGene tan : TanGene.values()) {
                                WorkingDog dog = create(level, breed);
                                dog.setGenes(color == CoatVariant.RED_HEELER ? CoatGene.RED : CoatGene.DARK,
                                        color == CoatVariant.RED_HEELER ? CoatGene.RED : CoatGene.LIGHT);
                                dog.setMarkingGenes(mask, mask, ticking, ticking, patch, patch,
                                        white, white, tan, tan);
                                String name = (color == CoatVariant.RED_HEELER ? "Red" : "Blue")
                                        + " Heeler | " + mask + " | " + ticking + " | " + patch
                                        + " | " + white + " | TAN " + tan;
                                add(result, color + "|" + mask + "|" + ticking + "|" + patch
                                        + "|" + white + "|" + tan, dog, name);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    private static WorkingDog create(ServerLevel level, DogBreed breed) {
        WorkingDog dog = ModEntities.WORKING_DOG.get().create(level);
        if (dog == null) throw new IllegalStateException("Could not create working dog test entity");
        dog.setBreed(breed);
        return dog;
    }

    private static void add(Map<String, Variation> result, String key, WorkingDog dog, String name) {
        result.putIfAbsent(key, new Variation(dog, name));
    }

    private static String pretty(DogBreed breed) {
        String[] words = breed.name().toLowerCase(Locale.ROOT).split("_");
        StringBuilder value = new StringBuilder();
        for (String word : words) {
            if (!value.isEmpty()) value.append(' ');
            value.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
        }
        return value.toString();
    }

    private record Variation(WorkingDog dog, String name) { }

    private WorkingDogCommands() { }
}
