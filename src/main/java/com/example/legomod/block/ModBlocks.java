package com.example.legomod.block;

import com.example.legomod.LegoMod;
import com.example.legomod.custom.LegoBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static void registerBlockItems(String id, Block block) {
        Item item = Registry.register(Registries.ITEM, Identifier.of(LegoMod.MOD_ID, id), new BlockItem(block, new Item.Settings()));
        if (item instanceof BlockItem) {
            ((BlockItem)item).appendBlocks(Item.BLOCK_ITEMS, item);
        }
    }

    public static Block register(String id, Block block) {
        registerBlockItems(id, block);
        return Registry.register(Registries.BLOCK, Identifier.of(LegoMod.MOD_ID, id), block);
    }

    //调用注册方法
//    public static final Block LEGO_BLOCK = register("lego_block", new LegoBlock(AbstractBlock.Settings.create().strength(2.0F, 10.0F)));
    public static final Block LEGO_BLOCK = register("lego_block", new LegoBlock(AbstractBlock.Settings.copy(Blocks.CAULDRON)));
    public static final Block RED_LEGO_BLOCK = register("red_lego_block", new LegoBlock(AbstractBlock.Settings.copy(Blocks.CAULDRON)));
    public static final Block BLUE_LEGO_BLOCK = register("blue_lego_block", new LegoBlock(AbstractBlock.Settings.copy(Blocks.CAULDRON)));
    public static final Block YELLOW_LEGO_BLOCK = register("yellow_lego_block", new LegoBlock(AbstractBlock.Settings.copy(Blocks.CAULDRON)));
    public static void registerModBlocks() {
        LegoMod.LOGGER.info("Registering Blocks");
    }

}
