package com.joshuastrutton.workingdogs.client;

import com.joshuastrutton.workingdogs.WorkingDog;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;

/** Drawn after every coat layer so markings can never obscure the eyes. */
public final class DogEyeLayer extends RenderLayer<Wolf, WolfModel<Wolf>> {
    private static final ResourceLocation EYES = ResourceLocation.fromNamespaceAndPath(
            "workingdogs", "textures/entity/working_dog/eyes.png");

    public DogEyeLayer(RenderLayerParent<Wolf, WolfModel<Wolf>> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffers, int packedLight,
                       Wolf wolf, float limbSwing, float limbSwingAmount,
                       float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!(wolf instanceof WorkingDog dog)) return;
        renderColoredCutoutModel(getParentModel(), EYES, poseStack,
                buffers, packedLight, dog, 0xFFFFFFFF);
    }
}
