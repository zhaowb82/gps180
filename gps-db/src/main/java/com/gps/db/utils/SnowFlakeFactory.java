package com.gps.db.utils;

import java.util.Objects;

/**
 * @author qulong
 * @date 2019/10/14 17:50
 * @description
 */
public class SnowFlakeFactory {

    private static SnowFlake snowFlake;


    public static SnowFlake getSnowFlake() {
        if (Objects.isNull(snowFlake)) {
            snowFlake = new SnowFlake(1, 1);
            return snowFlake;
        }
        return snowFlake;
    }

}
