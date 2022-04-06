package com.izofar.bygonenether.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.Util;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

public class ModFollowTemptation extends Behavior<PathfinderMob> {

    public static final int CLOSE_ENOUGH_DIST = 4;

    public ModFollowTemptation() {
        super(Util.make(() -> {
            ImmutableMap.Builder<MemoryModuleType<?>, MemoryStatus> builder = ImmutableMap.builder();
            builder.put(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED);
            builder.put(MemoryModuleType.IS_TEMPTED, MemoryStatus.REGISTERED);
            builder.put(MemoryModuleType.TEMPTING_PLAYER, MemoryStatus.VALUE_PRESENT);
            return builder.build();
        }));
    }

    private Optional<Player> getTemptingPlayer(PathfinderMob mob) {
        return mob.getBrain().hasMemoryValue(MemoryModuleType.IS_TEMPTED)
                ? mob.getBrain().getMemory(MemoryModuleType.TEMPTING_PLAYER)
                : Optional.empty();
    }

    protected boolean timedOut(long p_147488_) {
        return false;
    }

    protected boolean canStillUse(ServerLevel level, PathfinderMob mob, long p_147496_) {
        return this.getTemptingPlayer(mob).isPresent();
    }

    protected void start(ServerLevel level, PathfinderMob mob, long p_147507_) {

    }

    protected void stop(ServerLevel level, PathfinderMob mob, long p_147517_) {
        mob.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
    }

    protected void tick(ServerLevel level, PathfinderMob mob, long p_147525_) {
        Player player = this.getTemptingPlayer(mob).get();
        Brain<?> brain = mob.getBrain();
        if (mob.distanceToSqr(player) < CLOSE_ENOUGH_DIST * CLOSE_ENOUGH_DIST) {
            brain.eraseMemory(MemoryModuleType.WALK_TARGET);
        } else {
            brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityTracker(player, false), 1, 2));
        }

    }
}
