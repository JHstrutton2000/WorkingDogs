package com.joshuastrutton.workingdogs.client;

import com.joshuastrutton.workingdogs.ChihuahuaPhenotype;
import com.joshuastrutton.workingdogs.DogBreed;
import com.joshuastrutton.workingdogs.WorkingDog;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;

/** Renders shared inherited pattern and white-spotting loci for expanded breeds. */
public final class ChihuahuaMarkingLayer extends RenderLayer<Wolf, WolfModel<Wolf>> {
    private static final String ROOT = "textures/entity/working_dog/chihuahua/";

    public ChihuahuaMarkingLayer(RenderLayerParent<Wolf, WolfModel<Wolf>> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffers, int packedLight,
                       Wolf wolf, float limbSwing, float limbSwingAmount,
                       float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!(wolf instanceof WorkingDog dog) || !dog.usesGeneralCoatGenetics()) return;
        ChihuahuaPhenotype phenotype = dog.getChihuahuaPhenotype();

        switch (phenotype.pattern()) {
            case SOLID -> { }
            case TAN_POINTS -> draw(poseStack, buffers, packedLight, dog, "tan_points");
            case SABLE -> draw(poseStack, buffers, packedLight, dog, "sable");
            case BRINDLE -> draw(poseStack, buffers, packedLight, dog, "brindle");
            case MERLE -> draw(poseStack, buffers, packedLight, dog, "merle");
        }
        switch (phenotype.white()) {
            case NONE -> { }
            case CHEST -> draw(poseStack, buffers, packedLight, dog, "white_chest");
            case IRISH -> draw(poseStack, buffers, packedLight, dog, "white_irish");
            case PIEBALD -> draw(poseStack, buffers, packedLight, dog, "white_piebald");
        }
    }

    private void draw(PoseStack poseStack, MultiBufferSource buffers, int packedLight,
                      WorkingDog dog, String name) {
        renderColoredCutoutModel(getParentModel(), texture(name), poseStack,
                buffers, packedLight, dog, 0xFFFFFFFF);
    }

    private static ResourceLocation texture(String name) {
        return ResourceLocation.fromNamespaceAndPath("workingdogs", ROOT + name + ".png");
    }
}
