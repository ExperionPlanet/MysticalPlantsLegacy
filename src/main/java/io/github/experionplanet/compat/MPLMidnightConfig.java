package io.github.experionplanet.compat;

import eu.midnightdust.lib.config.MidnightConfig;

public class MPLMidnightConfig extends MidnightConfig {
    public static final String MAIN = "Main";
    public static final String CLIENT = "Client";

    public enum TOOL_MODEL_TYPE {
        DEFAULT, // Default
        OPTION2, // 2D
        OPTION3, // 3D
    }
    @Entry(category = MAIN, name = "Show debug items") public static boolean show_debug_item = false;
    @Entry(category = MAIN, name= "Show flower ring") public static boolean show_flower_rings = true;
    @Entry(category = CLIENT, name= "Animated flower ring") public static boolean animated_rings = true;
    @Entry(category = CLIENT, name = "Tool model type") public static TOOL_MODEL_TYPE tool_model_type = TOOL_MODEL_TYPE.DEFAULT;
    @Entry(category = CLIENT, name = "Show item fillings as durability bar") public static boolean show_fillings_durability = true;
    @Entry(category = CLIENT, name = "Show item fillings bar") public static boolean show_fillings_bar = true;
    @Entry(category = CLIENT, name = "Shulkura shoots") public static boolean shulkura_shoots = true;


}
