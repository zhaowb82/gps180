package com.gps.db.utils;

import java.util.Map;

public class AttrUtil {

    public static double getDouble(Map<String, Object> attributes, String key) {
        if (attributes == null) {
            return 0.0;
        }
        if (attributes.containsKey(key)) {
            return ((Number) attributes.get(key)).doubleValue();
        } else {
            return 0.0;
        }
    }
}
