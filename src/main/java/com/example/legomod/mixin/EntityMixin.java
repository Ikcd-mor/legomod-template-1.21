package com.example.legomod.mixin;

import com.example.legomod.effect.ModStatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static java.lang.Math.min;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow private Vec3d velocity; // 注意字段名需匹配实际混淆名（如 field_6283）

    @Redirect(
            method = "*", // 拦截所有方法中的 velocity 字段赋值
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/entity/Entity;velocity:Lnet/minecraft/util/math/Vec3d;",
                    opcode = Opcodes.PUTFIELD
            )
    )
    private void redirectVelocitySet(Entity instance, Vec3d newVelocity) {
        // 检查：实体是生物 且 有定身效果
        if (instance instanceof LivingEntity && ((LivingEntity) instance).hasStatusEffect(ModStatusEffects.IMMOBILIZE)) {
            double y = Math.min(newVelocity.y, 0); // Y轴速度限制为 <=0
            this.velocity = new Vec3d(0, y, 0);   // X/Z轴归零
        } else {
            this.velocity = newVelocity; // 不满足条件时保留原速度
        }
    }
}