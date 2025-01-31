package com.vetpetmon.wyrmsofnyrus.invasion.events;

import com.vetpetmon.synapselib.util.RNG;
import com.vetpetmon.wyrmsofnyrus.config.Debug;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.entity.creeped.EntityCreepPod;
import com.vetpetmon.wyrmsofnyrus.WyrmsOfNyrus;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class CreepPodDrop {
    public static void callEvent(double x, double z, World world){
        Entity entityToSpawn = new EntityCreepPod(world);
        if (!world.isRemote) {
            entityToSpawn.setLocationAndAngles((x + (RNG.PMRange(Invasion.maxEventDistance))), 280, (z + (RNG.PMRange(Invasion.maxEventDistance))), world.rand.nextFloat() * 360F,
                    0.0F);
            world.spawnEntity(entityToSpawn);
        }
        if (Debug.LOGGINGENABLED && Debug.DEBUGLEVEL >= 2) WyrmsOfNyrus.logger.info("A Creep Pod was spawned.");
    }
}
