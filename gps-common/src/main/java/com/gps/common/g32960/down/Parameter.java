package com.gps.common.g32960.down;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@Data
//@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Parameter {
    public enum SamplingState {
        UNKNOWN(0),
        ON(1),
        OFF(2),
        EXCEPTION(0xFE),
        INVALID(0xFF),
        UNRECOGNIZED(-1),
        ;

        public static final int UNKNOWN_VALUE = 0;
        public static final int ON_VALUE = 1;
        public static final int OFF_VALUE = 2;
        public static final int EXCEPTION_VALUE = 254;
        public static final int INVALID_VALUE = 255;


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
        public static SamplingState valueOf(int value) {
            return forNumber(value);
        }

        public static SamplingState forNumber(int value) {
            switch (value) {
                case 0: return UNKNOWN;
                case 1: return ON;
                case 2: return OFF;
                case 254: return EXCEPTION;
                case 255: return INVALID;
                default: return null;
            }
        }

        private final int value;

        private SamplingState(int value) {
            this.value = value;
        }

    }

    public enum ParameterCase {
        STORAGE_PERIOD(1),
        NORMAL_REPORT_INTERVAL(2),
        ALARM_REPORT_INTERVAL(3),
        MANAGEMENT_PLATFORM_DOMAIN_LENGTH(4),
        MANAGEMENT_PLATFORM_DOMAIN(5),
        MANAGEMENT_PLATFORM_PORT(6),
        HARDWARE_VERSION(7),
        FIRMWARE_VERSION(8),
        HEARBEAT_INTERVAL(9),
        TERMINAL_RESPONSE_TIMEOUT(10),
        PLATFORM_RESPONSE_TIMEOUT(11),
        LOGIN_RETRY_INTERVAL(12),
        PUBLIC_PLATFORM_DOMAIN_LENGTH(13),
        PUBLIC_PLATFORM_DOMAIN(14),
        PUBLIC_PLATFORM_PORT(15),
        SAMPLING(16),
        PARAMETER_NOT_SET(0);
        private final int value;
        private ParameterCase(int value) {
            this.value = value;
        }
        /**
         * @deprecated Use {@link #forNumber(int)} instead.
         */
        @Deprecated
        public static ParameterCase valueOf(int value) {
            return forNumber(value);
        }

        public static ParameterCase forNumber(int value) {
            switch (value) {
                case 1: return STORAGE_PERIOD;
                case 2: return NORMAL_REPORT_INTERVAL;
                case 3: return ALARM_REPORT_INTERVAL;
                case 4: return MANAGEMENT_PLATFORM_DOMAIN_LENGTH;
                case 5: return MANAGEMENT_PLATFORM_DOMAIN;
                case 6: return MANAGEMENT_PLATFORM_PORT;
                case 7: return HARDWARE_VERSION;
                case 8: return FIRMWARE_VERSION;
                case 9: return HEARBEAT_INTERVAL;
                case 10: return TERMINAL_RESPONSE_TIMEOUT;
                case 11: return PLATFORM_RESPONSE_TIMEOUT;
                case 12: return LOGIN_RETRY_INTERVAL;
                case 13: return PUBLIC_PLATFORM_DOMAIN_LENGTH;
                case 14: return PUBLIC_PLATFORM_DOMAIN;
                case 15: return PUBLIC_PLATFORM_PORT;
                case 16: return SAMPLING;
                case 0: return PARAMETER_NOT_SET;
                default: return null;
            }
        }
        public int getNumber() {
            return this.value;
        }
    }

    ParameterCase parameterCase;
//    oneof parameter {
        int storagePeriod;
        int normalReportInterval;
        int alarmReportInterval;
        int managementPlatformDomainLength;
        String managementPlatformDomain;
        int managementPlatformPort;
        String hardwareVersion;
        String firmwareVersion;
        int hearbeatInterval;
        int terminalResponseTimeout;
        int platformResponseTimeout;
        int loginRetryInterval;
        int publicPlatformDomainLength;
        String publicPlatformDomain;
        int publicPlatformPort;
        SamplingState sampling;
//    }


    public void setParameterCase(ParameterCase parameterCase) {
        this.parameterCase = parameterCase;
    }

    public void setStoragePeriod(int storagePeriod) {
        this.storagePeriod = storagePeriod;
        setParameterCase(ParameterCase.STORAGE_PERIOD);
    }

    public void setNormalReportInterval(int normalReportInterval) {
        this.normalReportInterval = normalReportInterval;
        setParameterCase(ParameterCase.NORMAL_REPORT_INTERVAL);
    }

    public void setAlarmReportInterval(int alarmReportInterval) {
        this.alarmReportInterval = alarmReportInterval;
        setParameterCase(ParameterCase.ALARM_REPORT_INTERVAL);
    }

    public void setManagementPlatformDomainLength(int managementPlatformDomainLength) {
        this.managementPlatformDomainLength = managementPlatformDomainLength;
        setParameterCase(ParameterCase.MANAGEMENT_PLATFORM_DOMAIN_LENGTH);
    }

    public void setManagementPlatformDomain(String managementPlatformDomain) {
        this.managementPlatformDomain = managementPlatformDomain;
        setParameterCase(ParameterCase.MANAGEMENT_PLATFORM_DOMAIN);
    }

    public void setManagementPlatformPort(int managementPlatformPort) {
        this.managementPlatformPort = managementPlatformPort;
        setParameterCase(ParameterCase.MANAGEMENT_PLATFORM_PORT);
    }

    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
        setParameterCase(ParameterCase.HARDWARE_VERSION);
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
        setParameterCase(ParameterCase.FIRMWARE_VERSION);
    }

    public void setHearbeatInterval(int hearbeatInterval) {
        this.hearbeatInterval = hearbeatInterval;
        setParameterCase(ParameterCase.HEARBEAT_INTERVAL);
    }

    public void setTerminalResponseTimeout(int terminalResponseTimeout) {
        this.terminalResponseTimeout = terminalResponseTimeout;
        setParameterCase(ParameterCase.TERMINAL_RESPONSE_TIMEOUT);
    }

    public void setPlatformResponseTimeout(int platformResponseTimeout) {
        this.platformResponseTimeout = platformResponseTimeout;
        setParameterCase(ParameterCase.PLATFORM_RESPONSE_TIMEOUT);
    }

    public void setLoginRetryInterval(int loginRetryInterval) {
        this.loginRetryInterval = loginRetryInterval;
        setParameterCase(ParameterCase.LOGIN_RETRY_INTERVAL);
    }

    public void setPublicPlatformDomainLength(int publicPlatformDomainLength) {
        this.publicPlatformDomainLength = publicPlatformDomainLength;
        setParameterCase(ParameterCase.PUBLIC_PLATFORM_DOMAIN_LENGTH);
    }

    public void setPublicPlatformDomain(String publicPlatformDomain) {
        this.publicPlatformDomain = publicPlatformDomain;
        setParameterCase(ParameterCase.PUBLIC_PLATFORM_DOMAIN);
    }

    public void setPublicPlatformPort(int publicPlatformPort) {
        this.publicPlatformPort = publicPlatformPort;
        setParameterCase(ParameterCase.PUBLIC_PLATFORM_PORT);
    }

    public void setSampling(SamplingState sampling) {
        this.sampling = sampling;
        setParameterCase(ParameterCase.SAMPLING);
    }
}
