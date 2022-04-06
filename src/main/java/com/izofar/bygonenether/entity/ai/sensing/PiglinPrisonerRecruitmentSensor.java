package com.izofar.bygonenether.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PiglinPrisonerRecruitmentSensor extends Sensor<PathfinderMob> {

    public static final int TEMPTATION_RANGE = 20;
    private static final TargetingConditions TEMPT_TARGETING = TargetingConditions.forNonCombat().range(TEMPTATION_RANGE).ignoreLineOfSight();

    public PiglinPrisonerRecruitmentSensor() { }

    protected void doTick(ServerLevel level, PathfinderMob mob) {
        Brain<?> brain = mob.getBrain();
        List<Player> list = level.players().stream()
                .filter(EntitySelector.NO_SPECTATORS).filter((player) -> TEMPT_TARGETING.test(mob, player))
                .filter((player) -> mob.closerThan(player, TEMPTATION_RANGE))
                .filter((player) -> playerIsTempter(player, brain))
                .sorted(Comparator.comparingDouble(mob::distanceToSqr))
                .collect(Collectors.toList());
        if (!list.isEmpty()) {
            Player player = list.get(0);
            brain.setMemory(MemoryModuleType.TEMPTING_PLAYER, player);
        } else {
            brain.eraseMemory(MemoryModuleType.TEMPTING_PLAYER);
        }

    }

    private boolean playerIsTempter(Player player, Brain<?> brain) {
        return brain.hasMemoryValue(MemoryModuleType.TEMPTING_PLAYER) && brain.getMemory(MemoryModuleType.TEMPTING_PLAYER).get().equals(player);
    }

    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(MemoryModuleType.TEMPTING_PLAYER);
    }
}
