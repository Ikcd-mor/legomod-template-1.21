package com.example.legomod.effect;


import com.example.legomod.mixin.EntityMixin;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.math.Vec3d;

public class ImmobilizeStatusEffects extends StatusEffect {

    protected ImmobilizeStatusEffects(StatusEffectCategory statusEffectCategory, int i) {
        super(statusEffectCategory, i);
    }
}