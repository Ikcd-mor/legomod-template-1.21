package com.example.legomod.datagen;

import com.example.legomod.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class ModENUSLanProvider extends FabricLanguageProvider {
    public ModENUSLanProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput,"en_us", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {

        translationBuilder.add(ModBlocks.LEGO_BLOCK,"Lego Block");
        translationBuilder.add(ModBlocks.RED_LEGO_BLOCK,"Red Lego Block");
        translationBuilder.add(ModBlocks.BLUE_LEGO_BLOCK,"Blue Lego Block");
        translationBuilder.add(ModBlocks.YELLOW_LEGO_BLOCK,"Yellow Lego Block");

        translationBuilder.add("effect.legomod.pain","Pain");
        translationBuilder.add("effect.legomod.immobilize","Immobilize");

        translationBuilder.add("itemGroup.lego_group", "Lego Group");
    }

}

