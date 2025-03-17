package com.example.legomod.datagen;

import com.example.legomod.block.ModBlocks;
import com.example.legomod.effect.ModStatusEffects;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModZHCNLanProvider extends FabricLanguageProvider{
    public ModZHCNLanProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput,"zh_cn", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, FabricLanguageProvider.TranslationBuilder translationBuilder) {

        translationBuilder.add(ModBlocks.LEGO_BLOCK,"乐高块");
        translationBuilder.add(ModBlocks.RED_LEGO_BLOCK,"红色乐高块");
        translationBuilder.add(ModBlocks.BLUE_LEGO_BLOCK,"蓝色乐高块");
        translationBuilder.add(ModBlocks.YELLOW_LEGO_BLOCK,"黄色乐高块");

        translationBuilder.add("effect.legomod.pain","疼痛");
        translationBuilder.add("effect.legomod.immobilize","定身");

        translationBuilder.add("itemGroup.lego_group", "乐高");
    }
}
