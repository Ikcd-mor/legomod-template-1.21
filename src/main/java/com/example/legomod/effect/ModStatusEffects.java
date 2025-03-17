package com.example.legomod.effect;

import com.example.legomod.LegoMod;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModStatusEffects {

    //只用这一个注册也可以
//    public static final RegistryEntry<StatusEffect> PAIN = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(LegoMod.MOD_ID, "tater"), new PainStatusEffects(StatusEffectCategory.HARMFUL, 0x808080));


    public static final RegistryEntry<StatusEffect> PAIN = register("pain", new PainStatusEffects(StatusEffectCategory.HARMFUL, 0x808080));
    public static final RegistryEntry<StatusEffect> IMMOBILIZE = register("immobilize", new ImmobilizeStatusEffects(StatusEffectCategory.HARMFUL, 0x808080));
//            .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,Identifier.of(LegoMod.MOD_ID,"effect.immobilize"),-1, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    private static RegistryEntry<StatusEffect> register(String id, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(LegoMod.MOD_ID, id), statusEffect);
    }

    public static void registerModStatusEffects() {
        LegoMod.LOGGER.info("Registering Status Effects");
    }
}
