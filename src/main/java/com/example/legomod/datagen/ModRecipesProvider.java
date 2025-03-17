package com.example.legomod.datagen;

import com.example.legomod.LegoMod;
import com.example.legomod.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModRecipesProvider extends FabricRecipeProvider {
    public ModRecipesProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.RED_LEGO_BLOCK)
                .input(ModBlocks.LEGO_BLOCK)
                .input(Items.TNT)
                .input(Items.RED_DYE)
                .criterion("has_item", conditionsFromItem(ModBlocks.LEGO_BLOCK))
                .offerTo(exporter, Identifier.of(LegoMod.MOD_ID, "red_lego_block"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.BLUE_LEGO_BLOCK)
                .input(ModBlocks.LEGO_BLOCK)
                .input(Items.DIAMOND)
                .input(Items.BLUE_DYE)
                .criterion("has_item", conditionsFromItem(ModBlocks.LEGO_BLOCK))
                .offerTo(exporter, Identifier.of(LegoMod.MOD_ID, "blue_lego_block"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.YELLOW_LEGO_BLOCK)
                .input(ModBlocks.LEGO_BLOCK)
                .input(Items.HONEY_BLOCK)
                .input(Items.YELLOW_DYE)
                .criterion("has_item", conditionsFromItem(ModBlocks.LEGO_BLOCK))
                .offerTo(exporter, Identifier.of(LegoMod.MOD_ID, "yellow_lego_block"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.LEGO_BLOCK)
                .pattern(" # ")
                .pattern("###")
                .pattern("###")
                .input('#', Ingredient.ofItems(Items.CLAY_BALL))
                .criterion("has_item", RecipeProvider.conditionsFromItem(Items.CLAY_BALL))
                .offerTo(exporter, Identifier.of(LegoMod.MOD_ID, "lego_block"));

    }
}
