package com.gps.common.g32960;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleState {
    public enum OperatingState {
        _1(0),
        RUN(1),
        CLOSE(2),
        OTHER(3),
        OP_EXCEPTION(254),
        OP_INVALID(255),
        UNRECOGNIZED(-1),
        ;
        public static final int _1_VALUE = 0;
        public static final int RUN_VALUE = 1;
        public static final int CLOSE_VALUE = 2;
        public static final int OTHER_VALUE = 3;
        public static final int OP_EXCEPTION_VALUE = 254;
        public static final int OP_INVALID_VALUE = 255;


        public final int getNumber() {
            if (this == UNRECOGNIZED) {
                throw new IllegalArgumentException(
                        "Can't get the number of an unknown enum value.");
            }
            return value;
        }

        public static OperatingState forNumber(int value) {
            switch (value) {
                case 0: return _1;
                case 1: return RUN;
                case 2: return CLOSE;
                case 3: return OTHER;
                case 254: return OP_EXCEPTION;
                case 255: return OP_INVALID;
                default: return null;
            }
        }
        private final int value;

        OperatingState(int value) {
            this.value = value;
        }
    }

    public enum ChargeState {
        _2(0),
        PARKING(1),
        DRIVING(2),
        OUTAGE(3),
        COMPLETED(4),
        CHARGE_EXCEPTION(254),
        CHARGE_INVALID(255),
        UNRECOGNIZED(-1),
        ;

        public static final int _2_VALUE = 0;
        public static final int PARKING_VALUE = 1;
        public static final int DRIVING_VALUE = 2;
        public static final int OUTAGE_VALUE = 3;
        public static final int COMPLETED_VALUE = 4;
        public static final int CHARGE_EXCEPTION_VALUE = 254;
        public static final int CHARGE_INVALID_VALUE = 255;


        public final int getNumber() {
            if (this == UNRECOGNIZED) {
                throw new IllegalArgumentException(
                        "Can't get the number of an unknown enum value.");
            }
            return value;
        }

        public static ChargeState forNumber(int value) {
            switch (value) {
                case 0:
                    return _2;
                case 1:
                    return PARKING;
                case 2:
                    return DRIVING;
                case 3:
                    return OUTAGE;
                case 4:
                    return COMPLETED;
                case 254:
                    return CHARGE_EXCEPTION;
                case 255:
                    return CHARGE_INVALID;
                default:
                    return null;
            }
        }

        private final int value;

        private ChargeState(int value) {
            this.value = value;
        }

    }

    public enum OperationMode {
        /**
         * <pre>
         * unused
         * </pre>
         *
         * <code>_3 = 0;</code>
         */
        _3(0),
        /**
         * <code>ELECTRIC_ONLY = 1;</code>
         */
        ELECTRIC_ONLY(1),
        /**
         * <code>MIXED = 2;</code>
         */
        MIXED(2),
        /**
         * <code>FUEL_ONLY = 3;</code>
         */
        FUEL_ONLY(3),
        /**
         * <code>OM_EXCEPTION = 254;</code>
         */
        OM_EXCEPTION(254),
        /**
         * <code>OM_INVALID = 255;</code>
         */
        OM_INVALID(255),
        UNRECOGNIZED(-1),
        ;

        /**
         * <pre>
         * unused
         * </pre>
         *
         * <code>_3 = 0;</code>
         */
        public static final int _3_VALUE = 0;
        /**
         * <code>ELECTRIC_ONLY = 1;</code>
         */
        public static final int ELECTRIC_ONLY_VALUE = 1;
        /**
         * <code>MIXED = 2;</code>
         */
        public static final int MIXED_VALUE = 2;
        /**
         * <code>FUEL_ONLY = 3;</code>
         */
        public static final int FUEL_ONLY_VALUE = 3;
        /**
         * <code>OM_EXCEPTION = 254;</code>
         */
        public static final int OM_EXCEPTION_VALUE = 254;
        /**
         * <code>OM_INVALID = 255;</code>
         */
        public static final int OM_INVALID_VALUE = 255;


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
        public static OperationMode valueOf(int value) {
            return forNumber(value);
        }

        public static OperationMode forNumber(int value) {
            switch (value) {
                case 0:
                    return _3;
                case 1:
                    return ELECTRIC_ONLY;
                case 2:
                    return MIXED;
                case 3:
                    return FUEL_ONLY;
                case 254:
                    return OM_EXCEPTION;
                case 255:
                    return OM_INVALID;
                default:
                    return null;
            }
        }

        private final int value;

        private OperationMode(int value) {
            this.value = value;
        }

    }

    public enum DcInverterState {
        /**
         * <pre>
         * unused
         * </pre>
         *
         * <code>_4 = 0;</code>
         */
        _4(0),
        /**
         * <code>ON = 1;</code>
         */
        ON(1),
        /**
         * <code>OFF = 2;</code>
         */
        OFF(2),
        /**
         * <code>DC_EXCEPTION = 254;</code>
         */
        DC_EXCEPTION(254),
        /**
         * <code>DC_INVALID = 255;</code>
         */
        DC_INVALID(255),
        UNRECOGNIZED(-1),
        ;

        public static final int _4_VALUE = 0;
        public static final int ON_VALUE = 1;
        public static final int OFF_VALUE = 2;
        public static final int DC_EXCEPTION_VALUE = 254;
        public static final int DC_INVALID_VALUE = 255;

        public final int getNumber() {
            if (this == UNRECOGNIZED) {
                throw new IllegalArgumentException(
                        "Can't get the number of an unknown enum value.");
            }
            return value;
        }

        public static DcInverterState forNumber(int value) {
            switch (value) {
                case 0:
                    return _4;
                case 1:
                    return ON;
                case 2:
                    return OFF;
                case 254:
                    return DC_EXCEPTION;
                case 255:
                    return DC_INVALID;
                default:
                    return null;
            }
        }

        private final int value;

        DcInverterState(int value) {
            this.value = value;
        }
    }


    OperatingState operatingState;
    ChargeState chargingState;
    OperationMode operationMode;
    float speed;
    double mileage;
    float voltage;
    float current;
    int stateOfCharge;
    DcInverterState dcInverterState;
    int gearPosition;
    int insulance;

    int acceleratorTravel; //加速踏板行程
    int brakeTravel;  //刹车踏板行程
}
