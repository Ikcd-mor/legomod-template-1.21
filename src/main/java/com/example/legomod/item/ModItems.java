package com.example.legomod.item;

import com.example.legomod.LegoMod;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ModItems {
    private static Item registerItems(String id, Item item) {
        //原版注册
        //return Registry.register(Registries.ITEM, RegistryKey.of(Registries.ITEM.getKey(),Identifier.of(LegoMod.MOD_ID,id)), item);
        //精简后注册
        return Registry.register(Registries.ITEM, Identifier.of(LegoMod.MOD_ID, id), item);
    }

    //物品注册


    public static void registerModItems() {
        LegoMod.LOGGER.info("Registering Items");
    }
}
