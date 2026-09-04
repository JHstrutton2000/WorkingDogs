package com.joshuastrutton.workingdogs.registry;

import com.joshuastrutton.workingdogs.WorkingDogs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(WorkingDogs.MOD_ID);

    public static final DeferredBlock<Block> BLUE_HEELER_WOOL =
            BLOCKS.register("blue_heeler_wool", () -> new Block(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.BLUE_WOOL)));

    public static final DeferredBlock<Block> RED_HEELER_WOOL =
            BLOCKS.register("red_heeler_wool", () -> new Block(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.RED_WOOL)));

    private ModBlocks() {}
}
