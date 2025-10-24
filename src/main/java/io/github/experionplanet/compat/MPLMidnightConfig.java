package io.github.experionplanet.compat;

import eu.midnightdust.lib.config.MidnightConfig;

public class MPLMidnightConfig extends MidnightConfig {
    public static final String TEXT = "Main";

    public enum TOOL_MODEL_TYPE {
        DEFAULT, // Default
        OPTION2, // 2D
        OPTION3, // 3D
    }
    @Comment(category = TEXT) public static Comment spacer_yeahh;
    @Entry(category = TEXT, name= "Show flower ring")
    public static boolean show_flower_rings = true;

    @Entry(category = TEXT, name= "Animated flower ring")
    public static boolean animated_rings = true;

    @Entry(category = TEXT, name = "Shulkura shoots")
    public static boolean shulkura_shoots = true;

    @Entry(category = TEXT, name = "Tool model type")
    public static TOOL_MODEL_TYPE tool_model_type = TOOL_MODEL_TYPE.DEFAULT;

    @Entry(category = TEXT, name = "Show debug items")
    public static boolean show_debug_item = false;


}
