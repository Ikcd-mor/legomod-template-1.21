package com.example.legomod.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PainStatusEffects extends StatusEffect {
    // 存储实体UUID和最后触发时间的映射
    private static final Map<UUID, Long> LAST_TRIGGER_TIMES = new HashMap<>();

    protected PainStatusEffects(StatusEffectCategory statusEffectCategory, int i) {
        super(statusEffectCategory, i);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        World world = entity.getWorld();
        if (world.isClient()) {
            return true; // 客户端不处理
        }

        UUID uuid = entity.getUuid();
        long currentTime = world.getTime(); // 获取当前时间（以刻为单位）

        // 检查最后触发时间
        if (!LAST_TRIGGER_TIMES.containsKey(uuid) ||
                currentTime - LAST_TRIGGER_TIMES.get(uuid) >= 40) {
            // 更新最后触发时间
            LAST_TRIGGER_TIMES.put(uuid, currentTime);

            // 执行效果逻辑
            if (!entity.getType().isIn(EntityTypeTags.SKELETONS)) {
                if (entity.getHealth() > 1.0F) {
                    entity.damage(entity.getDamageSources().magic(), 1.0F);
                }
            }
        }

        return true;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // 不依赖剩余时间，直接返回 true
        return true;
    }

    // 清理过期记录（可选）
    public static void cleanupExpiredRecords(World world) {
        long currentTime = world.getTime();
        LAST_TRIGGER_TIMES.entrySet().removeIf(entry -> currentTime - entry.getValue() > 400); // 清理超过20秒未触发的记录
    }
}
