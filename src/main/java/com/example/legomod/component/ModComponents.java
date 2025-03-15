package com.example.legomod.component;

import com.example.legomod.LegoMod;
import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModComponents {
    public static final ComponentType<Boolean> IMMOBILIZE = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(LegoMod.MOD_ID, "immobilize"), // 用你的模组 ID
            ComponentType.<Boolean>builder().codec(Codec.BOOL).build() // 使用 BOOL 编解码器
    );

    public static void registerModItemGroups() {
        LegoMod.LOGGER.info("Registering Components");
    }
}
