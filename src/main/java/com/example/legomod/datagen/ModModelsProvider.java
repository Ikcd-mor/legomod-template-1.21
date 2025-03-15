package com.example.legomod.datagen;

import com.example.legomod.LegoMod;
import com.example.legomod.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelsProvider extends FabricModelProvider {
    public ModModelsProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleState(ModBlocks.LEGO_BLOCK);
        blockStateModelGenerator.registerSimpleState(ModBlocks.RED_LEGO_BLOCK);
        blockStateModelGenerator.registerSimpleState(ModBlocks.BLUE_LEGO_BLOCK);
        blockStateModelGenerator.registerSimpleState(ModBlocks.YELLOW_LEGO_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModBlocks.LEGO_BLOCK.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.RED_LEGO_BLOCK.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.BLUE_LEGO_BLOCK.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.YELLOW_LEGO_BLOCK.asItem(), Models.GENERATED);
    }
}