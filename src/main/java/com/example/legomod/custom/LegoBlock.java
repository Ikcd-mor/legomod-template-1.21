package com.example.legomod.custom;

import com.example.legomod.effect.ModStatusEffects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class LegoBlock extends Block {
    // 触发记录存储（使用UUID和坐标组合作为关键字防止内存泄漏和相互干扰）
    private static final Map<String, int[]> ENTITY_RECORDS = new ConcurrentHashMap<>();

    // 保持原有的形状定义
    private static final VoxelShape TRIGGER_SHAPE = Stream.of(
            Block.createCuboidShape(0, 8, 0, 16, 8.25, 16),
            Block.createCuboidShape(3, 10, 3, 6, 10.25, 6),
            Block.createCuboidShape(3, 10, 10, 6, 10.25, 13),
            Block.createCuboidShape(10, 10, 3, 13, 10.25, 6),
            Block.createCuboidShape(10, 10, 10, 13, 10.25, 13)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    public static final VoxelShape SHAPE = Stream.of(
            Block.createCuboidShape(0, 0, 0, 16, 8, 16),
            Block.createCuboidShape(3, 8, 3, 6, 10, 6),
            Block.createCuboidShape(3, 8, 10, 6, 10, 13),
            Block.createCuboidShape(10, 8, 3, 13, 10, 6),
            Block.createCuboidShape(10, 8, 10, 13, 10, 13)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    public LegoBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    // 实体碰撞检测（使用精确触发区域）
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient() && entity instanceof LivingEntity living) {
            // 将触发区域转换为世界坐标
            Box triggerBox = TRIGGER_SHAPE.getBoundingBox().offset(pos);

            // 检测实体碰撞盒是否与触发区域相交
            if (entity.getBoundingBox().intersects(triggerBox)) {
                // 赋予中毒效果1秒
                living.addStatusEffect(new StatusEffectInstance(ModStatusEffects.PAIN, 20, 0));
                handleEntityTrigger(living, pos, (ServerWorld) world);
            }
        }
    }

    // 根据不同的方块做出不同的处理
    private void dealWay(LivingEntity entity, BlockPos pos, ServerWorld world) {
        BlockState state = world.getBlockState(pos);
        switch (state.getBlock().getTranslationKey()) {
            case "block.legomod.lego_block":
                // 处理白色方块的情况
                entity.damage(world.getDamageSources().magic(), 5.0F);
                break;
            case "block.legomod.red_lego_block":
                // 处理红色方块的情况
                world.createExplosion(entity, pos.getX(), pos.getY()+3, pos.getZ(), 4.0F, World.ExplosionSourceType.TNT);
                break;
            case "block.legomod.blue_lego_block":
                // 处理蓝色方块的情况
                entity.damage(world.getDamageSources().magic(), 18.0F);
                break;
            case "block.legomod.yellow_lego_block":
                if (entity.hasStatusEffect(ModStatusEffects.IMMOBILIZE)) {
                    System.out.println("已经被禁锢了");
                }
                // 处理黄色方块的情况
                entity.damage(world.getDamageSources().magic(), 5.0F);
                ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(ModStatusEffects.IMMOBILIZE, 1200, 0, false, false, false));
                break;
            default:
                // 默认处理
                break;
        }
    }

    private void handleEntityTrigger(LivingEntity entity, BlockPos pos, ServerWorld world) {
        UUID uuid = entity.getUuid();
        String key = uuid.toString() + ":" + pos.getX() + ":" + pos.getY() + ":" + pos.getZ();

        // 首次触发时记录
        if (!ENTITY_RECORDS.containsKey(key)) {
            dealWay(entity, pos, world);
            ENTITY_RECORDS.put(key, new int[]{pos.getX(), pos.getY(), pos.getZ()});
            world.scheduleBlockTick(pos, this, 10);
        } else {
            // 检查存储的坐标与当前pos是否不同
            int[] storedPos = ENTITY_RECORDS.get(key);
            BlockPos originalPos = new BlockPos(storedPos[0], storedPos[1], storedPos[2]);
            if (!originalPos.equals(pos)) {
                dealWay(entity, pos, world);
                ENTITY_RECORDS.put(key, new int[]{pos.getX(), pos.getY(), pos.getZ()});
                world.scheduleBlockTick(pos, this, 10);
            }
        }
    }

    // 定时检测逻辑
    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        // 清理已离开所有方块的生物记录
        ENTITY_RECORDS.entrySet().removeIf(entry -> {
            String[] parts = entry.getKey().split(":");
            UUID uuid = UUID.fromString(parts[0]);
            BlockPos recordPos = new BlockPos(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
            Entity entity = world.getEntity(uuid);
            if (entity == null) {
                return true; // 实体不存在，移除记录
            }
            // 检查实体是否仍在任何方块上
            Box triggerArea = TRIGGER_SHAPE.getBoundingBox()
                    .offset(recordPos) // 转换为世界坐标
                    .expand(0.1); // 扩展0.1格防止边界误差
            return !entity.getBoundingBox().intersects(triggerArea);
        });

        // 重新调度检测（仅当仍有实体时）
        if (!ENTITY_RECORDS.isEmpty()) {
            world.scheduleBlockTick(pos, this, 10);
        }
    }
}