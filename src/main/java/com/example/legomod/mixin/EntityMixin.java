package com.example.legomod.mixin;

import com.example.legomod.effect.ModStatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow
    private Vec3d velocity;

    @Inject(
            method = "setVelocity(Lnet/minecraft/util/math/Vec3d;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onSetVelocity(Vec3d velocity, CallbackInfo ci) {
        Entity instance = (Entity) (Object) this;
        if (instance instanceof LivingEntity && ((LivingEntity) instance).hasStatusEffect(ModStatusEffects.IMMOBILIZE)) {
            double y = Math.min(velocity.y, 0); // Y轴速度限制为 <=0
            this.velocity = new Vec3d(0, y, 0);   // X/Z轴归零
            ci.cancel(); // 取消原方法的执行
        }
    }
}