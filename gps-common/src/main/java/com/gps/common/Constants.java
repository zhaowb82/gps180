package com.gps.common;

/**
 * 常量类
 */
public class Constants {

    /**
     * -------------------------------------命令相关------------------------------------
     */

    public static final String COMMANDS_TOPIC = "commands"; // api -> gate
    public static final String POSITION_TOPIC = "positions"; // gate -> data
    public static final String NOTIFYUI_TOPIC = "notifyui"; // data -> api
    public static final String IMPORTCMD_TOPIC = "importcmd"; // gate -> api
    public static final String TO809_TOPIC = "to809"; // data -> 809
    public static final String POS_KEY = "pos_key";
    public static final String CMD_KEY = "cmd_key";

    //命令唯一标识
    public static final String USER_ID = "userId";
    public static final String USER_PHONE = "phone";
    public static final String PLATFORM = "platform";

}
