package com.gps.common.g32960;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FuelCell {
    public enum HighVoltageDCState {
        /**
         * <pre>
         * unused
         * </pre>
         *
         * <code>_1 = 0;</code>
         */
        _1(0),
        /**
         * <pre>
         * 工作
         * </pre>
         *
         * <code>ON = 1;</code>
         */
        ON(1),
        /**
         * <pre>
         * 断开
         * </pre>
         *
         * <code>OFF = 2;</code>
         */
        OFF(2),
        /**
         * <code>HIGH_VOLTAGE_DC_EXCEPTION = 254;</code>
         */
        HIGH_VOLTAGE_DC_EXCEPTION(0xFE),
        /**
         * <code>HIGH_VOLTAGE_DC_INVALID = 255;</code>
         */
        HIGH_VOLTAGE_DC_INVALID(0xFF),
        UNRECOGNIZED(-1),
        ;

        /**
         * <pre>
         * unused
         * </pre>
         *
         * <code>_1 = 0;</code>
         */
        public static final int _1_VALUE = 0;
        /**
         * <pre>
         * 工作
         * </pre>
         *
         * <code>ON = 1;</code>
         */
        public static final int ON_VALUE = 1;
        /**
         * <pre>
         * 断开
         * </pre>
         *
         * <code>OFF = 2;</code>
         */
        public static final int OFF_VALUE = 2;
        /**
         * <code>HIGH_VOLTAGE_DC_EXCEPTION = 254;</code>
         */
        public static final int HIGH_VOLTAGE_DC_EXCEPTION_VALUE = 254;
        /**
         * <code>HIGH_VOLTAGE_DC_INVALID = 255;</code>
         */
        public static final int HIGH_VOLTAGE_DC_INVALID_VALUE = 255;


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
        public static HighVoltageDCState valueOf(int value) {
            return forNumber(value);
        }

        public static HighVoltageDCState forNumber(int value) {
            switch (value) {
                case 0: return _1;
                case 1: return ON;
                case 2: return OFF;
                case 254: return HIGH_VOLTAGE_DC_EXCEPTION;
                case 255: return HIGH_VOLTAGE_DC_INVALID;
                default: return null;
            }
        }


        private final int value;

        private HighVoltageDCState(int value) {
            this.value = value;
        }

    }
    double fuelCellVoltage;                    // 燃料电池电压
    double fuelCellCurrent;                    // 燃料电池电流
    double fuelConsumptionRate;                // 燃料消耗率
    int totalNumberOfFctp;                  // 燃料电池温度探针总数
    List<Integer> probeTemperatureValue;      // 探针温度值
    double highestTempOfHydrogenSystem;      // 氢系统中最高温度
    int highestTempProbeCodeOfHydSys;    // 氢系统中最高温度探针代号
    int highestConOfHydrogen;               // 氢气最高浓度
    int highestHyConSensorCode;            // 氢气最高浓度传感器代号
    double hydrogenMaxPressure;               // 氢气最高压力
    int hydrogenMaxPressureSensorCode;    // 氢气最高压力传感器代号
    HighVoltageDCState highVoltageDcState;               // 高压DC/DC状态
}
