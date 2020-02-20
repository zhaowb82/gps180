package com.gps.common.g32960;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MotorState {

    public enum MotorStatus {
        _1(0),
        CONSUMING(1),
        GENERATING(2),
        OFF(3),
        READY(4),
        MOTOR_EXCEPTION(0xFE),
        MOTOR_INVALID(0xFF),
        UNRECOGNIZED(-1),
        ;

        public static final int _1_VALUE = 0;
        public static final int CONSUMING_VALUE = 1;
        public static final int GENERATING_VALUE = 2;
        public static final int OFF_VALUE = 3;
        public static final int READY_VALUE = 4;
        public static final int MOTOR_EXCEPTION_VALUE = 254;
        public static final int MOTOR_INVALID_VALUE = 255;


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
        public static MotorStatus valueOf(int value) {
            return forNumber(value);
        }

        public static MotorStatus forNumber(int value) {
            switch (value) {
                case 0: return _1;
                case 1: return CONSUMING;
                case 2: return GENERATING;
                case 3: return OFF;
                case 4: return READY;
                case 254: return MOTOR_EXCEPTION;
                case 255: return MOTOR_INVALID;
                default: return null;
            }
        }

        private final int value;

        private MotorStatus(int value) {
            this.value = value;
        }

    }

    long motorSeq;
    MotorStatus status;
    int controllerTemperature;
    int motorSpeed;
    float motorTorque; //驱动电机转矩
    int motorTemperature;
    float controllerVoltage;
    float controllerCurrent; //电机控制器直流母线电流
}
