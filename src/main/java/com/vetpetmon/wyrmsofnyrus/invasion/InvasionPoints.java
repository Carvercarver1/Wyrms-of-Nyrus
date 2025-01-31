package com.vetpetmon.wyrmsofnyrus.invasion;

import com.vetpetmon.wyrmsofnyrus.WyrmVariables;
import net.minecraft.world.World;

/**
 * A collection of functions used to mess with invasion points.
 * I made this specifically because I hated writing so much code every
 * time I wanted to add points to the invasion
 *
 * Even worse, there's no dedicated getter. I'm fixing that as we speak.
 */
public class InvasionPoints {
    private static double pointBuffer;
    public static double get(World w) {return WyrmVariables.WorldVariables.get(w).wyrmInvasionPoints;}
    public static double getDifficulty(World w) {return WyrmVariables.WorldVariables.get(w).wyrmInvasionDifficulty;}
    public static void setDifficulty(World w, float input) {
        WyrmVariables.WorldVariables.get(w).wyrmInvasionDifficulty = input;
        sync(w);
    }

    /**
     * Adds invasion points to the world.
     * Syncs automatically. Now you can do this in one line.
     * @param w World
     * @param i Value to add
     */
    public static void add(World w, int i){
        pointBuffer += i;
        if (pointBuffer >= 1) updatePoints(w,pointBuffer);
    }
    public static void add(World w, double i){
        pointBuffer += i;
        if (pointBuffer >= 1) updatePoints(w,pointBuffer);
    }

    public static void updatePoints(World w, double buffer){
        WyrmVariables.WorldVariables.get(w).wyrmInvasionPoints += (int) buffer;
        pointBuffer = 0;
        sync(w);
    }

    // Unused at the moment.
    /**
     * Removes invasion points from the world.
     * Syncs automatically. Now you can do this in one line.
     * @param w World
     * @param i Value to add
     */
    public static void subtract(World w, int i){
        WyrmVariables.WorldVariables.get(w).wyrmInvasionPoints -= i;
        sync(w);
    }
    public static void subtract(World w, double i){
        WyrmVariables.WorldVariables.get(w).wyrmInvasionPoints -= i;
        sync(w);
    }

    /**
     * Syncs the variable change to the world.
     */
    private static void sync(World w) {
        WyrmVariables.WorldVariables.get(w).syncData(w);
    }
}
