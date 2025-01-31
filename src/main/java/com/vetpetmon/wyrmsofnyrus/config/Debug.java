package com.vetpetmon.wyrmsofnyrus.config;

import com.vetpetmon.wyrmsofnyrus.WyrmsOfNyrus;
import net.minecraftforge.common.config.Configuration;

import static com.vetpetmon.synapselib.util.CFG.*;

public class Debug {

    public static boolean LOGGINGENABLED;

    public static int DEBUGLEVEL;

    public static void loadFromConfig(Configuration config) {

        final String CATEGORY = "Debugging";

        config.addCustomCategoryComment(CATEGORY, "\nMod dev & tester settings\n");

        LOGGINGENABLED = createConfigBool(config, CATEGORY, "Enable logging", "Spams the console with various logs. If you enable this, make sure to edit the variable down below. \nDo not enable this unless if you're a developer, tester, or a modpack developer that needs to figure out what went wrong or why X thing isn't working. This is NOT intended for normal players to use at all. Default: false", false);
        DEBUGLEVEL = createConfigInt(config, CATEGORY, "Debug level", "The amount of console printing you want to destroy your hard drive with. Values above 10 won't do anything different. The larger this number, the more gets outputted. All increments of levels also include previous levels' loggings, so be careful about using this for extensive periods of times. \n- Level 1: minimal logging, will simply give a few warnings. \n- Level 2: Will output events like invasion event occurrences or invasion stage increments. \n- Level 3: Will output events like wyrmlings growing up \n- Level 4: Will output block mutations \n- Level 5: Will reveal block updates, entity deaths, etc. \n- Level 6: Major variable changes will be outputted into the log. \n- Level 7: All script variable changes will now output to the log, entity animation changes, and most sound plays are logged. \n- Level 8: EVERY METHOD CALL WILL BE OUTPUTTED INTO THE LOG. \n- Level 9: YOU BETTER HAVE A GOOD REASON WHY YOU WANT TO SEE EVERY SINGLE INPUT PUT INTO THOSE METHOD CALLS. \n- Level 10: I am not responsible if you brick your RAM or PC. You asked for it. Does everything in the previous levels, but also outputs more stuff like entity ticks and their NBT values, as well as every RNG roll, every... Thing. Turn back now, save your PC before it's too late. Default: 1", 1);
        if (DEBUGLEVEL > 10) DEBUGLEVEL = 10;
        else if (DEBUGLEVEL < 1) DEBUGLEVEL = 1;
        if (LOGGINGENABLED) WyrmsOfNyrus.logger.info("Debug logging enabled! The current depth and level of debug logging is: " + DEBUGLEVEL);
    }
}
