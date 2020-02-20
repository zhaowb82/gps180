
package com.gps.common.helper;

public final class BitUtil {

    private BitUtil() {
    }

    public static int getBit(long number, int index, int len) {
        int ret = 0;
        for (int i = index; i < index + len; i++) {
            int a = (int) (number & (1 << i));
            ret |= a;
        }
        return ret >> index;
    }

    public static boolean check(long number, int index) {
        return (number & (1 << index)) != 0;
    }

    public static long setBit(long number, int index, boolean set) {
        if (set) {
            return number | (1 << index);
        }
        return number & (~(number & (1 << index)));
    }

    public static int between(int number, int from, int to) {
        return (number >> from) & ((1 << to - from) - 1);
    }

    public static int from(int number, int from) {
        return number >> from;
    }

    public static int to(int number, int to) {
        return between(number, 0, to);
    }

    public static long between(long number, int from, int to) {
        return (number >> from) & ((1L << to - from) - 1L);
    }

    public static long from(long number, int from) {
        return number >> from;
    }

    public static long to(long number, int to) {
        return between(number, 0, to);
    }


}
