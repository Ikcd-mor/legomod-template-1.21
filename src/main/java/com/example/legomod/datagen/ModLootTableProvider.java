package com.example.legomod.datagen;

import com.example.legomod.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.LEGO_BLOCK);
        addDrop(ModBlocks.RED_LEGO_BLOCK);
        addDrop(ModBlocks.BLUE_LEGO_BLOCK);
        addDrop(ModBlocks.YELLOW_LEGO_BLOCK);
    }
}
