package com.gps.common.g32960;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Engine {
    public enum EngineState {
        _1(0),
        START(1),
        CLOSE(2),
        ENGINE_EXCEPTION(0xFE),
        ENGINE_INVALID(0xFF),
        UNRECOGNIZED(-1),
        ;

        public static final int _1_VALUE = 0;
        public static final int START_VALUE = 1;
        public static final int CLOSE_VALUE = 2;
        public static final int ENGINE_EXCEPTION_VALUE = 254;
        public static final int ENGINE_INVALID_VALUE = 255;

        public final int getNumber() {
            if (this == UNRECOGNIZED) {
                throw new IllegalArgumentException(
                        "Can't get the number of an unknown enum value.");
            }
            return value;
        }

        public static EngineState forNumber(int value) {
            switch (value) {
                case 0: return _1;
                case 1: return START;
                case 2: return CLOSE;
                case 254: return ENGINE_EXCEPTION;
                case 255: return ENGINE_INVALID;
                default: return null;
            }
        }

        private final int value;

        private EngineState(int value) {
            this.value = value;
        }

    }
    EngineState engineState;           // 发动机状态
    int crankshaftSpeed;             // 曲轴转速
    double fuelConsumptionRate;       // 燃料消耗率
}
