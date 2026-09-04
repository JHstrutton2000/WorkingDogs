package com.joshuastrutton.workingdogs.client;

import com.joshuastrutton.workingdogs.DogBreed;
import com.joshuastrutton.workingdogs.HeelerPhenotype;
import com.joshuastrutton.workingdogs.WorkingDog;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;

/** Draws independently inherited markings over the base heeler coat. */
public final class HeelerMarkingLayer extends RenderLayer<Wolf, WolfModel<Wolf>> {
    private static final String ROOT = "textures/entity/working_dog/heeler/";

    public HeelerMarkingLayer(RenderLayerParent<Wolf, WolfModel<Wolf>> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffers, int packedLight,
                       Wolf wolf, float limbSwing, float limbSwingAmount,
                       float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!(wolf instanceof WorkingDog dog)
                || dog.getBreed() != DogBreed.AUSTRALIAN_CATTLE_DOG) {
            return;
        }

        HeelerPhenotype phenotype = dog.getHeelerPhenotype();

        draw(poseStack, buffers, packedLight, dog,
                switch (phenotype.ticking()) {
                    case LIGHT -> texture("ticking_light");
                    case MEDIUM -> texture("ticking_medium");
                    case HEAVY -> texture("ticking_heavy");
                });

        switch (phenotype.patches()) {
            case NONE -> { }
            case SMALL -> draw(poseStack, buffers, packedLight, dog, texture("patch_small"));
            case LARGE -> draw(poseStack, buffers, packedLight, dog, texture("patch_large"));
        }

        switch (phenotype.mask()) {
            case NONE -> { }
            case LEFT -> draw(poseStack, buffers, packedLight, dog, texture("mask_left"));
            case RIGHT -> draw(poseStack, buffers, packedLight, dog, texture("mask_right"));
            case DOUBLE -> draw(poseStack, buffers, packedLight, dog, texture("mask_double"));
        }

        switch (phenotype.whiteMark()) {
            case NONE -> { }
            case BENTLEY -> draw(poseStack, buffers, packedLight, dog, texture("white_bentley"));
            case BLAZE -> draw(poseStack, buffers, packedLight, dog, texture("white_blaze"));
        }

        draw(poseStack, buffers, packedLight, dog,
                switch (phenotype.tan()) {
                    case LIGHT -> texture("tan_light");
                    case NORMAL -> texture("tan_normal");
                    case RICH -> texture("tan_rich");
                });
    }

    private void draw(PoseStack poseStack, MultiBufferSource buffers, int packedLight,
                      WorkingDog dog, ResourceLocation texture) {
        renderColoredCutoutModel(
                getParentModel(), texture, poseStack, buffers, packedLight, dog, 0xFFFFFFFF);
    }

    private static ResourceLocation texture(String name) {
        return ResourceLocation.fromNamespaceAndPath("workingdogs", ROOT + name + ".png");
    }
}
