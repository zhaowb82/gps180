package com.gps.common.g32960.down;

public enum CommandType {
    /**
     * <code>UNUSED = 0;</code>
     */
    UNUSED(0),
    /**
     * <code>UPGRADE = 1;</code>
     */
    UPGRADE(1),
    /**
     * <code>SHUTDOWN = 2;</code>
     */
    SHUTDOWN(2),
    /**
     * <code>TERMINAL_RESET = 3;</code>
     */
    TERMINAL_RESET(3),
    /**
     * <code>FACTORY_RESET = 4;</code>
     */
    FACTORY_RESET(4),
    /**
     * <code>DISCONECT = 5;</code>
     */
    DISCONECT(5),
    /**
     * <code>WARNING = 6;</code>
     */
    WARNING(6),
    /**
     * <code>SAMPLING_CHECK = 7;</code>
     */
    SAMPLING_CHECK(7),
    /**
     * <code>CUSTOM_CONTROL = 129;</code>
     */
    CUSTOM_CONTROL(0x81),
    UNRECOGNIZED(-1),
    ;

    /**
     * <code>UNUSED = 0;</code>
     */
    public static final int UNUSED_VALUE = 0;
    /**
     * <code>UPGRADE = 1;</code>
     */
    public static final int UPGRADE_VALUE = 1;
    /**
     * <code>SHUTDOWN = 2;</code>
     */
    public static final int SHUTDOWN_VALUE = 2;
    /**
     * <code>TERMINAL_RESET = 3;</code>
     */
    public static final int TERMINAL_RESET_VALUE = 3;
    /**
     * <code>FACTORY_RESET = 4;</code>
     */
    public static final int FACTORY_RESET_VALUE = 4;
    /**
     * <code>DISCONECT = 5;</code>
     */
    public static final int DISCONECT_VALUE = 5;
    /**
     * <code>WARNING = 6;</code>
     */
    public static final int WARNING_VALUE = 6;
    /**
     * <code>SAMPLING_CHECK = 7;</code>
     */
    public static final int SAMPLING_CHECK_VALUE = 7;
    /**
     * <code>CUSTOM_CONTROL = 129;</code>
     */
    public static final int CUSTOM_CONTROL_VALUE = 129;


    public final int getNumber() {
        if (this == UNRECOGNIZED) {
            throw new IllegalArgumentException(
                    "Can't get the number of an unknown enum value.");
        }
        return value;
    }

    /**
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @Deprecated
    public static CommandType valueOf(int value) {
        return forNumber(value);
    }

    public static CommandType forNumber(int value) {
        switch (value) {
            case 0: return UNUSED;
            case 1: return UPGRADE;
            case 2: return SHUTDOWN;
            case 3: return TERMINAL_RESET;
            case 4: return FACTORY_RESET;
            case 5: return DISCONECT;
            case 6: return WARNING;
            case 7: return SAMPLING_CHECK;
            case 129: return CUSTOM_CONTROL;
            default: return null;
        }
    }

    private final int value;

    private CommandType(int value) {
        this.value = value;
    }
}
