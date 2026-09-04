package com.joshuastrutton.workingdogs.client;

import com.joshuastrutton.workingdogs.WorkingDogs;
import com.joshuastrutton.workingdogs.registry.ModEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = WorkingDogs.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class WorkingDogsClient {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.WORKING_DOG.get(), WorkingDogRenderer::new);
    }

    private WorkingDogsClient() {}
}
