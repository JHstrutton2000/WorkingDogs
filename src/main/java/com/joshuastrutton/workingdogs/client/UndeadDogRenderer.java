package com.joshuastrutton.workingdogs.client;

import com.joshuastrutton.workingdogs.SkeletonDog;
import com.joshuastrutton.workingdogs.WorkingDogs;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;

public final class UndeadDogRenderer extends WolfRenderer {
    private static final ResourceLocation ZOMBIE_TEXTURE = ResourceLocation.fromNamespaceAndPath(
            WorkingDogs.MOD_ID, "textures/entity/undead_dog/zombie_dog.png");
    private static final ResourceLocation SKELETON_TEXTURE = ResourceLocation.fromNamespaceAndPath(
            WorkingDogs.MOD_ID, "textures/entity/undead_dog/skeleton_dog.png");

    public UndeadDogRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(Wolf dog) {
        return dog instanceof SkeletonDog ? SKELETON_TEXTURE : ZOMBIE_TEXTURE;
    }
}
