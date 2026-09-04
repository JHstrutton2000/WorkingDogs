package com.joshuastrutton.workingdogs.client;

import com.joshuastrutton.workingdogs.WorkingDog;
import com.joshuastrutton.workingdogs.ChihuahuaHeadGene;
import com.joshuastrutton.workingdogs.BreedBodyProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;

public class WorkingDogRenderer extends WolfRenderer {
    private final DockableWolfModel dogModel;
    private static final ResourceLocation PALE = ResourceLocation.withDefaultNamespace("textures/entity/wolf/wolf_pale.png");
    private static final ResourceLocation ASHEN = ResourceLocation.withDefaultNamespace("textures/entity/wolf/wolf_ashen.png");
    private static final ResourceLocation BLACK = ResourceLocation.withDefaultNamespace("textures/entity/wolf/wolf_black.png");
    private static final ResourceLocation CHESTNUT = ResourceLocation.withDefaultNamespace("textures/entity/wolf/wolf_chestnut.png");
    private static final ResourceLocation RUSTY = ResourceLocation.withDefaultNamespace("textures/entity/wolf/wolf_rusty.png");
    private static final ResourceLocation SNOWY = ResourceLocation.withDefaultNamespace("textures/entity/wolf/wolf_snowy.png");
    private static final ResourceLocation SPOTTED = ResourceLocation.withDefaultNamespace("textures/entity/wolf/wolf_spotted.png");
    private static final ResourceLocation STRIPED = ResourceLocation.withDefaultNamespace("textures/entity/wolf/wolf_striped.png");

    public WorkingDogRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.dogModel = new DockableWolfModel(context.bakeLayer(ModelLayers.WOLF));
        this.model = dogModel;
        addLayer(new HeelerMarkingLayer(this));
        addLayer(new ChihuahuaMarkingLayer(this));
        // Must remain last: coat overlays are allowed to cross the face UV,
        // but they must never cover the dog's eyes.
        addLayer(new DogEyeLayer(this));
    }

    @Override
    public void render(Wolf wolf, float entityYaw, float partialTick,
                       PoseStack poseStack, MultiBufferSource buffers, int packedLight) {
        WorkingDog dog = wolf instanceof WorkingDog workingDog ? workingDog : null;
        boolean chihuahua = dog != null && dog.getBreed() == com.joshuastrutton.workingdogs.DogBreed.CHIHUAHUA;
        BreedBodyProfile profile = dog == null
                ? BreedBodyProfile.of(com.joshuastrutton.workingdogs.DogBreed.GERMAN_SHEPHERD, false)
                : BreedBodyProfile.of(dog.getBreed(), dog.hasFullCoat());
        dogModel.setTailDocked(dog != null && dog.isTailDocked());
        dogModel.setBreedShape(profile,
                !chihuahua || dog.getChihuahuaPhenotype().head() == ChihuahuaHeadGene.APPLE);
        poseStack.pushPose();
        float sizeScale = dog == null ? 1.0F : dog.getSizeScale();
        poseStack.scale(profile.scale() * sizeScale,
                profile.scale() * sizeScale,
                profile.scale() * sizeScale);
        try {
            super.render(wolf, entityYaw, partialTick, poseStack, buffers, packedLight);
        } finally {
            poseStack.popPose();
            // The renderer/model instance is shared by every dog.
            dogModel.setTailDocked(false);
            dogModel.setBreedShape(BreedBodyProfile.of(
                    com.joshuastrutton.workingdogs.DogBreed.GERMAN_SHEPHERD, false), true);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(Wolf wolf) {
        if (!(wolf instanceof WorkingDog dog)) return super.getTextureLocation(wolf);
        if (dog.usesGeneralCoatGenetics()) {
            return switch (dog.getChihuahuaPhenotype().color()) {
                case BLACK -> BLACK;
                case BLUE -> ASHEN;
                case CHOCOLATE -> CHESTNUT;
                case RED -> RUSTY;
                case FAWN -> PALE;
                case CREAM, WHITE -> SNOWY;
            };
        }
        return switch (dog.getCoatVariant()) {
            // Keep Minecraft's exact wolf UV atlas as the opaque base. All
            // heeler-specific markings are transparent layers drawn above it.
            case BLUE_HEELER -> ASHEN;
            case RED_HEELER -> RUSTY;
            case BLACK_AND_TAN -> STRIPED;
            case GOLDEN -> PALE;
            case BLACK_LAB -> BLACK;
            case YELLOW_LAB -> SNOWY;
            case CHOCOLATE_LAB -> CHESTNUT;
            case BLACK_AND_WHITE -> SPOTTED;
            case CHIHUAHUA_SHORT_HAIR -> CHESTNUT;
            case CHIHUAHUA_LONG_HAIR -> PALE;
        };
    }
}
