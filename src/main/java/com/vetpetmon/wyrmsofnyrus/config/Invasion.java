package com.vetpetmon.wyrmsofnyrus.config;

import com.vetpetmon.synapselib.util.blacklistUtil;
import com.vetpetmon.wyrmsofnyrus.WyrmsOfNyrus;
import net.minecraft.block.Block;
import net.minecraftforge.common.config.Configuration;

import java.util.ArrayList;

import static com.vetpetmon.synapselib.util.CFG.*;

public class Invasion {
    public static boolean invasionEnabled, invasionStartsNaturally;
    public static int invasionStartMode, invasionStartTime, invasionStartChance, invasionEventFrequency;

    public static boolean probingEnabled;
    public static int invasionPointsPerKill;

    public static int minWyrmsHexepod, maxWyrmsHexepod, minWyrmsCallouspod, maxWyrmsCallouspod;
    public static int visitorDropPodFrequency, visitorDropPodFrequencyVariation;

    public static int iPointsIStage1Threshold, iPointsIStage2Threshold, iPointsIStage3Threshold, iPointsIStage4Threshold, iPointsIStage5Threshold, iPointsIStage6Threshold;

    public static int maxEventDistance;

    public static boolean creepEnabled, creepNewInactivity;
    public static int creepSpreadRate, creepTickRate, normCreepwyrmCreepSpeed, direCreepwyrmCreepSpeed, creephiveCreepSpeed;
    public static float creepSpreadPoints, creepSpreadMaxHardness;
    public static String[] invalidBlocksForCreepspread;
    public static boolean CSBlockBLEnabled;
    public static ArrayList<Block> invalidBlocks;

    public static String[] iBdef = {"minecraft:furnace", "minecraft:brick_block", "minecraft:bone_block", "minecraft:bedrock", "minecraft:concrete", "minecraft:concrete_powder", "minecraft:end_bricks", "minecraft:end_stone", "minecraft:glass", "minecraft:jukebox", "minecraft:nether_brick", "minecraft:red_nether_brick", "minecraft:noteblock", "minecraft:observer", "minecraft:obsidian", "minecraft:packed_ice", "minecraft:prismarine", "minecraft:purpur_block", "minecraft:purpur_pillar", "minecraft:quartz_block", "minecraft:sponge", "minecraft:stained_glass", "minecraft:wool", "minecraft:stonebrick"};

    public static void loadFromConfig(Configuration config, int id) {

        final String CATEGORY = "Invasion";
        config.addCustomCategoryComment(CATEGORY,  "\nThe Wyrm Invasion is the main mechanic of this mod, with a fully-fledged event system with threats that keep players on edge.\n");
        config.setCategoryRequiresWorldRestart(CATEGORY, true);

        final String CATEGORYTWO = "Hive Creep";
        config.addCustomCategoryComment(CATEGORYTWO,  "\nEverything involving Hive Creep\n");
        config.setCategoryRequiresWorldRestart(CATEGORYTWO, true);

        final String CATEGORYTHREE = "Drop Pods";
        config.addCustomCategoryComment(CATEGORYTHREE,  "\nEverything involving wyrm spawns from the Invasion system.\n");
        config.setCategoryRequiresWorldRestart(CATEGORYTHREE, false);

        minWyrmsHexepod = createConfigInt(config, CATEGORYTHREE,"Hexe min spawns" ,"Minimum wyrms to spawn from Hexe pods. Will still always spawn at least 1 wyrm. Hexe pods always spawn wyrmlings that grow into various castes of wyrms except royals. Default: 1", ConfigBase.presetInts(1, 2, 3, id));
        maxWyrmsHexepod = createConfigInt(config, CATEGORYTHREE,"Hexe max spawns" ,"Maximum wyrms to spawn from Hexe pods. Default: 3", ConfigBase.presetInts(3, 6, 7, id));
        if (minWyrmsHexepod < 1) minWyrmsHexepod = 1;

        minWyrmsCallouspod = createConfigInt(config, CATEGORYTHREE,"Callous min spawns" ,"Minimum wyrms to spawn from Callous pods. Default: 1", ConfigBase.presetInts(1, 2, 2, id));
        maxWyrmsCallouspod = createConfigInt(config, CATEGORYTHREE,"Callous max spawns" ,"Maximum wyrms to spawn from Callous pods. Default: 2", ConfigBase.presetInts(2, 3, 4, id));
        if (minWyrmsCallouspod < 1) minWyrmsCallouspod = 1;

        invasionStartMode = createConfigInt(config, CATEGORY,"Invasion Start Mode" ,"0 = Random chance, 1 = after x days, 2 = hybrid (time & chance). Default: 2", ConfigBase.presetInts(2, 1, 1, id));
        invasionStartTime = createConfigInt(config, CATEGORY,"Invasion Start Time" ,"Number of days until wyrms can start to invade. Default: 20", ConfigBase.presetInts(20, 10, 10, id));
        invasionStartChance = createConfigInt(config, CATEGORY,"Invasion Start Chance" ,"1 in x chance to occur each day before wyrms start invading. Default: 50", 50);

        invasionEventFrequency = createConfigInt(config, CATEGORY,"Invasion Event Frequency" ,"Every x minutes, an invasion event takes place. Default: 8", ConfigBase.presetInts(8, 8, 7, id));

        visitorDropPodFrequency = createConfigInt(config, CATEGORYTHREE,"Visitor drop pod frequency" ,"Delay for each drop pod spawn from The Visitor. Default: 3500", ConfigBase.presetInts(3500, 3050, 2500, id));
        visitorDropPodFrequencyVariation = createConfigInt(config, CATEGORYTHREE,"Visitor drop pod frequency variation" ,"Random variation added to the drop delays, adds -x to x ticks to delay. Default: 500", ConfigBase.presetInts(500, 550, 250, id));

        invasionEnabled = createConfigBool(config, CATEGORY, "Invasion enabled", "Enables the invasion system. Many functions of the mod will not work if this is off, including other sub-systems. Default: true", true);
        if (!invasionEnabled) WyrmsOfNyrus.logger.info("Invasion module has been disabled");
        invasionStartsNaturally = createConfigBool(config, CATEGORY, "Invasion starts naturally", "Allows the invasion to start naturally without needing admin commands. Default: true", true);

        //invasion stage point thresholds
        iPointsIStage1Threshold = createConfigInt(config, CATEGORY,"Stage 1 Invasion Point threshold" ,"How many points are required to reach this phase of the alien invasion. Phase 0 (arriving) takes place UNDER this value. Default: 1000", 1000);
        iPointsIStage2Threshold = createConfigInt(config, CATEGORY,"Stage 2 Invasion Point threshold" ,"How many points are required to reach this phase of the alien invasion. Default: 5000", 5000);
        iPointsIStage3Threshold = createConfigInt(config, CATEGORY,"Stage 3 Invasion Point threshold" ,"How many points are required to reach this phase of the alien invasion. Default: 10000", 10000);
        iPointsIStage4Threshold = createConfigInt(config, CATEGORY,"Stage 4 Invasion Point threshold" ,"How many points are required to reach this phase of the alien invasion. Default: 50000", 50000);
        iPointsIStage5Threshold = createConfigInt(config, CATEGORY,"Stage 5 Invasion Point threshold" ,"How many points are required to reach this phase of the alien invasion. Default: 100000", 100000);
        iPointsIStage6Threshold = createConfigInt(config, CATEGORY,"Stage 6 Invasion Point threshold" ,"How many points are required to reach this phase of the alien invasion. Default: 2500000", 2500000);
        validatePhaseThresholds(); // Validate everything

        maxEventDistance = createConfigInt(config, CATEGORY, "Max event distance", "All invasion events take place a certain distance away from the player. Increasing this range makes it less likely that events happen near the player, but may cause performance hitches due to potential chunkloading. Usually keep this number in increments of 16 (Chunk x/z size). Default is calculated for Minecraft's usual 12 chunk render radius. Default: 192", 192);

        invasionPointsPerKill = createConfigInt(config, CATEGORY, "Invasion Points Per Kill", "Wyrms gain invasion points for every kill. Set this to 0 to disable this feature entirely. Default: 1", ConfigBase.presetInts(1,1,2,id));

        probingEnabled = createConfigBool(config, CATEGORY, "Probing enabled", "Probers deal heavy damage and every kill advances the invasion by 5 points, compared to only one, IF the entity can be sampled (hit) more than once. One-shots or each hit adds 2 points. They also have longer aggro range and fly faster. Set to false to disable this feature and make probers less dangerous.", ConfigBase.presetBools(false, true, true, id));

        creepTickRate = createConfigInt(config, CATEGORYTWO, "Creep tickrate", "Every n world ticks, hive creep blocks will tick and roll to see if they spread or not. 20 ticks = 1 second. Default: 500", ConfigBase.presetInts(500, 475,450, id));
        creepNewInactivity = createConfigBool(config, CATEGORYTWO, "Experimental creep inactivity algorithm", "By default, hive creep blocks just have a chance to turn inactive. The new algorithm checks all blocks in range for a non-creepable block. However, the new algorithm can be buggy and produce weird results, such as hive creep turning inactive ASAP, or it never turning inactive. Default: false", false);
        creepEnabled = createConfigBool(config, CATEGORYTWO, "Creep enabled", "If The Creep is enabled or not. This stops spread, and also renders Creepwyrms useless (Also disabling their spawning.) Default: true", true);
        if (!creepEnabled) WyrmsOfNyrus.logger.info("Creep module has been disabled");
        if (creepNewInactivity) WyrmsOfNyrus.logger.warn("We are using the new Creep inactivity algorithm. May the suns be on your sides. (This is EXPERIMENTAL)");
        creepSpreadRate = createConfigInt(config, CATEGORYTWO, "Creep spread speed", "1 to n chance every tick that a hive creep blocks actually does something. Some blocks will tick much slower, like creepstone. Increase this number if you're seeing TPS drops. Default: 10", 10);
        creepSpreadPoints = createConfigDouble(config, CATEGORYTWO, "Creep spread points", "Every time a creep block is created, the invasion points increase. If Invasion is not enabled, this won't work at all. It is recommended you should keep this number as a decimal unless if you want pain... Default: 0.015", 0.015);
        creepSpreadMaxHardness = createConfigDouble(config, CATEGORYTWO, "Creep spread max hardness", "Maximum hardness of a block that can be infested. Can automatically generate a blacklist this way for other mods if you're too lazy to add to the blacklist below. Default: 2.45", 2.45);
        invalidBlocksForCreepspread = createConfigStringList(config, CATEGORYTWO, "Creepable block blacklist", ("Blacklist of blocks hive creep can not spread to. Blocks that are not considered a full block by the MC engine do not need to be included here.\nWARNING: EXPERIMENTAL FEATURE. Disabled/enable in the 2nd config option. Is automatically set to true in development builds."), iBdef);
        CSBlockBLEnabled = createConfigBool(config, CATEGORYTWO, "Creepable block blacklist enabled", "Enables the creepable block blacklist.", true);
        FinalizeiBdef();

        // Creepwyrm creeping speeds
        normCreepwyrmCreepSpeed = createConfigInt(config, CATEGORYTWO, "Creepwyrm creep speed", "The speed at which normal Creepwyrms spread The Creep. Every x entity updates, the creepwyrm runs a check and creeps over a valid block if found. Lower this to make it faster, or increase it even further to make creepwyrms do their thing a lot slower. Default: 200", ConfigBase.presetInts(200, 150, 125, id));
        direCreepwyrmCreepSpeed = createConfigInt(config, CATEGORYTWO, "Dire Creepwyrm creep speed", "The speed at which Dire Creepwyrms spread The Creep. Every x entity updates, the creepwyrm runs a check and creeps over a valid block if found. Lower this to make it faster, or increase it even further to make creepwyrms do their thing a lot slower./n(WARNING: AS THIS MOB CREATES ACTIVE CREEPED BLOCKS, KEEP THIS VALUE VERY HIGH TO AVOID BLOCK UPDATE SPAM BUILDUP) Default: 1800", 1800);
        creephiveCreepSpeed = createConfigInt(config, CATEGORYTWO, "Creep Hive creep speed", "The speed at which Creep Hives spread The Creep. Every x entity updates, the creep hive runs a check and creeps over a valid block if found. Lower this to make it faster, or increase it even further to make creep hives do their thing a lot slower./n(WARNING: AS THIS MOB CREATES ACTIVE CREEPED BLOCKS, KEEP THIS VALUE VERY HIGH TO AVOID BLOCK UPDATE SPAM BUILDUP) Default: 1800", 1800);

    }

    public static boolean isCreepEnabled() {
        return creepEnabled;
    }

    /**
     * Casts the invalid block blacklist for creep spread. First casts to List, then ArrayList<String>, and finally ArrayList<Block>.
     */
    public static void FinalizeiBdef() {
        invalidBlocks = blacklistUtil.castToBlockBL(invalidBlocksForCreepspread);
    }

    /**
     * Validates user input concerning phase thresholds.
     */
    public static void validatePhaseThresholds(){
        if(Debug.LOGGINGENABLED)WyrmsOfNyrus.logger.info("Validating invasion stage point thresholds...");
        boolean validationFailed = false;
            if (iPointsIStage1Threshold > iPointsIStage2Threshold) {
                validationFailed = true;
                WyrmsOfNyrus.logger.warn("Stage 1 Invasion Point threshold was more than Stage 2 Invasion Point threshold, resetting to default...");
            }
            else if (iPointsIStage2Threshold > iPointsIStage3Threshold) {
                validationFailed = true;
                WyrmsOfNyrus.logger.warn("Stage 2 Invasion Point threshold was more than Stage 3 Invasion Point threshold, resetting to default...");
            }
            else if (iPointsIStage3Threshold > iPointsIStage4Threshold) {
                validationFailed = true;
                WyrmsOfNyrus.logger.warn("Stage 3 Invasion Point threshold was more than Stage 4 Invasion Point threshold, resetting to default...");
            }
            else if (iPointsIStage4Threshold > iPointsIStage5Threshold) {
                validationFailed = true;
                WyrmsOfNyrus.logger.warn("Stage 4 Invasion Point threshold was more than Stage 5 Invasion Point threshold, resetting to default...");
            }
            else if (iPointsIStage5Threshold > iPointsIStage6Threshold) {
                validationFailed = true;
                WyrmsOfNyrus.logger.warn("Stage 5 Invasion Point threshold was more than Stage 6 Invasion Point threshold, resetting both to default...");
            }
            if (validationFailed) {
                WyrmsOfNyrus.logger.warn("Had to fall back to default point thresholds to avoid logic errors. Please fix your configuration file to use custom thresholds again.");
                iPointsIStage1Threshold = 1000;
                iPointsIStage2Threshold = 5000;
                iPointsIStage3Threshold = 10000;
                iPointsIStage4Threshold = 50000;
                iPointsIStage5Threshold = 100000;
                iPointsIStage6Threshold = 2500000;
            }
        if(Debug.LOGGINGENABLED)WyrmsOfNyrus.logger.info("All invasion stage point thresholds are validated.");
    }
}
