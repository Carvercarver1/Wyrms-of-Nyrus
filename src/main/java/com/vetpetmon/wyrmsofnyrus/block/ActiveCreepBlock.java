package com.vetpetmon.wyrmsofnyrus.block;


import com.vetpetmon.wyrmsofnyrus.config.Debug;
import com.vetpetmon.wyrmsofnyrus.synapselib.RNG;
import com.vetpetmon.wyrmsofnyrus.synapselib.rangeCheck;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ActiveCreepBlock {

    public static void CreepSpread(BlockPos pos, World world, IBlockState inactiveBlockName) {
        boolean doCheck = ((RNG.getIntRangeInclu(0,50)) >= 49);
        if (doCheck) {
            if (rangeCheck.material(world, pos, 4, BlockMaterials.CREEP, true)) {
                if (Debug.LOGGINGENABLED && Debug.DEBUGLEVEL >= 8)
                    System.out.println("Debugging: creep block at "
                            + pos
                            + " saw a non-creep block, so it can spread somewhere, thus, it was not turned inactive.");
            }
            else {
                if (Debug.LOGGINGENABLED && Debug.DEBUGLEVEL >= 7)
                    System.out.println("Debugging: creep block at " + pos + " was turned inactive");
                world.setBlockState(pos, inactiveBlockName, 3);
            }
        }
    }


}
