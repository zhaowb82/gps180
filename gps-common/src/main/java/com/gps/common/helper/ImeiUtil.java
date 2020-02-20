package com.gps.common.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImeiUtil {

    /**
     * 计算imei最后一位数据与5 的模
     * @param imei
     * @return
     */
    public static int getImeiRemainder(String imei) {
        if (imei == null) {
            return 2;
        } else if (imei.length() < 9) {        //imei为车牌号
            String regEx="[^0-9]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(imei);
            String numStr = m.replaceAll("").trim();
            return Integer.parseInt(numStr.substring(numStr.length() - 1)) % 5;
        } else {        //正常imei号
           return  Integer.parseInt(imei.substring(imei.length() - 1)) % 5;
        }
    }
}
