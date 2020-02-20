package com.gps.common.helper;

import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description 工具类
 */
//@Slf4j
public class BytesUtil {

    /**
     * 将8字节整数变成字节数组
     *
     * @param value
     * @return
     */
    public static byte[] longToByteArray(long value) {
        byte[] b = new byte[8];
        for (int i = 0; i < 8; i++) {
            int offset = (b.length - 1 - i) * 8;
            b[i] = (byte) ((value >>> offset) & 0xFF);
        }
        return b;
    }

    /**
     * 将4个字节的byte数组变成整数
     *
     * @return
     */
    public static int bytes2int4(byte[] b) {
        int s = 0;
        s = ((b[0] & 0xff) << 24 | (b[1] & 0xff) << 16 | (b[2] & 0xff) << 8 | (b[3] & 0xff));
        return s;
    }

    /**
     * @Description:bytes转int
     * @param :bytes : bytes length < 5
     * @return
     * @throws Exception
     */
    public static int parseBytesToInt(byte[] bytes) {
        int length = bytes.length;
        int intValue = 0;
        for (int i = 0; i < length; i++) {
            intValue <<= 8;
            intValue |= (bytes[i] & 0xff);
        }
        return intValue;
    }

    /**
     * @Description:bytes转int(二进制数转换成十进制数)(北斗)
     * @param :args
     * @return String
     * @throws Exception
     */
    public static String bdBytesToInt(String msgBytes) {
        String tempInt = Integer.valueOf(msgBytes, 2).toString();
        return tempInt;
    }

    /**
     * 将两个字节的byte数组变成整数
     *
     * @return
     */
    public static int bytes2int2(byte[] b) {
        int s = 0;
        s = ((b[0] & 0xff) << 8 | (b[1] & 0xff));
        return s;
    }

    public static short parseBytesToShort(byte[] bytes) {
        int length = bytes.length;
        short intValue = 0;
        for (int i = 0; i < length; i++) {
            intValue <<= 8;
            intValue |= (bytes[i] & 0xff);
        }
        return intValue;
    }

    /**
     * 上下两个方法千万别人为是相同的，进行删除一个 T808协议类名需要忽略掉0 因为数据库里没有
     *
     * @Description:将十进制转为为16机制 例如：new byte[]{1,2,3,17}转化为01020311
     * @param :args
     * @return
     * @throws Exception
     */
    public static String bcdToStr(byte[] bytes) {
        StringBuffer temp = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
            temp.append((byte) (bytes[i] & 0x0f));
        }

        // 表示把第一位的0忽略 例如：1返回1 19返回13
        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp.toString().substring(1) : temp.toString();

    }

    /**
     * @Description:将十进制转为为16机制 例如：new byte[]{1,2,3,17}转化为01020311 记录仪需要使用带0的日期
     * @param :args
     * @return
     * @throws Exception
     */
    public static String BcdToStr(byte[] bytes) {
        StringBuffer temp = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
            temp.append((byte) (bytes[i] & 0x0f));
        }

        return temp.toString();// 表示第一位不忽略，例如：1返回01 19返回13

    }

    /**
     * 把10进制变成16进制
     *
     * @param b
     *            传过来的是十进制
     * @return 返回的是16进制
     */
    public static String bcdToStr(byte b) {
        StringBuffer temp = new StringBuffer(2);

        temp.append((byte) ((b & 0xf0) >>> 4));
        temp.append((byte) (b & 0x0f));

        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp.toString().substring(1) : temp.toString();
    }

    /**
     * @Description:字节转化为Long类型
     * @param :args
     * @return
     * @throws Exception
     */
    public static long parseBytesToLong(byte[] bytes) {
        int length = bytes.length;
        long longValue = 0;
        for (int i = 0; i < bytes.length; i++) {
            longValue <<= 8;
            longValue |= (bytes[i] & 0xff);
        }
        return longValue;
    }

    /**
     * 作用：切字节数据
     *
     * @param byteIndex
     *            ：第几位字节数组开始
     * @param byteLength
     *            ：字节数组的长度
     * @param bytes
     *            ：字节数组
     * @return：返回新数组
     */
    public static byte[] cutBytes(int byteIndex, int byteLength, byte[] bytes) {
        byte[] result = new byte[byteLength];
        System.arraycopy(bytes, byteIndex, result, 0, byteLength);
        return result;
    }

    /**
     * @Description:获取int类型数据中的某一位
     * @param :bitIndex : 0 ~ 7
     * @param :byteIndex : < bytes.length
     * @param :bytes : bytes array
     * @return
     * @throws Exception
     */
    public static int getBitValue(int byteIndex, int bitIndex, byte[] bytes) {

        byte byteValue = bytes[byteIndex];
        return byteValue >> 7 - bitIndex & 0x01;

    }

    /**
     * @Description:判断int类型数据中哪一位是不是= 1，如果是返回true，否则返回false
     * @param :byteIndex：字节的起始位置(从左边数) bitIndex：对应位第几位的值是不是等于1(从左边数)
     * @return:如果是返回true，否则返回false
     * @throws Exception
     */
    public static boolean getBooleanValue(int byteIndex, int bitIndex, byte[] bytes) {
        byte byteValue = bytes[byteIndex];
        return ((byteValue >> 7 - bitIndex & 0x01) == 1) ? true : false;
    }

    /**
     * @Description:判断byte字节中哪一位二进制是不是= 1，如果是返回true，否则返回false
     * @param :bitIndex：对应位第几位的值是不是等于1(从左边数)
     * @return:如果是返回true，否则返回false
     * @throws Exception 7, BytesUtil.int2bytes4(3)[3])
     */
    public static boolean getBooleanValues(int bitIndex, byte bytes) {

        String bytesToString = getBinaryStrFromByte(bytes);
        String bit = bytesToString.substring(bitIndex, bitIndex + 1);
        int bits = Integer.parseInt(bit);
        return (bits == 1) ? true : false;

    }

    /**
     * * 把byte转化成2进制字符串 * @param b * @return
     */
    public static String getBinaryStrFromByte(byte b) {
        String result = "";
        byte a = b;
        ;
        for (int i = 0; i < 8; i++) {
            byte c = a;
            a = (byte) (a >> 1);
            a = (byte) (a << 1);
            if (a == c) {
                result = "0" + result;
            } else {
                result = "1" + result;
            }
            a = (byte) (a >> 1);
        }
        return result;
    }

    /**
     * * 把16进制字符串转化成2进制字符串 * @param b * @return
     */
    public static String hexString2binaryString(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0)
            return null;
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }

    /**
     * @Description:获取某一位的值
     * @param :byteIndex 字节数字引索
     * @param :bytes 字节数组
     * @return
     * @throws Exception
     */
    public static byte getByte(int byteIndex, byte[] bytes) {
        return bytes[byteIndex];
    }

    /**
     * @Description:获取两个字节大端数据
     * @param :byteIndex 字节数字引索
     * @param :bytes 字节数组
     * @return
     * @throws Exception
     */
    public static byte[] getBigWord(int byteIndex, byte[] bytes) {
        byte[] result = new byte[2];
        result[1] = bytes[byteIndex];
        result[0] = bytes[byteIndex + 1];
        return result;
    }

    /**
     * @Description:获取四个字节大端数据
     * @param :byteIndex 字节数字引索
     * @param :bytes 字节数组
     * @return
     * @throws Exception
     */
    public static byte[] getBigDWord(int byteIndex, byte[] bytes) {
        byte[] result = new byte[4];
        result[3] = bytes[byteIndex];
        result[2] = bytes[byteIndex + 1];
        result[1] = bytes[byteIndex + 2];
        result[0] = bytes[byteIndex + 3];
        return result;
    }

    public static byte[] getWord(int byteIndex, byte[] bytes) {
        return cutBytes(byteIndex, 2, bytes);
    }

    public static byte[] getDWord(int byteIndex, byte[] bytes) {

        return cutBytes(byteIndex, 4, bytes);
    }

    /**
     * @Description:获取消息体长度
     * @param :bitLength :< 32
     * @param :bytes :bytes.length < 4
     * @return
     * @throws Exception
     */
    public static int getBitsValue(int bitIndex, int bitLength, byte[] bytes) {
        // 变成32位
        int intValue = parseBytesToInt(bytes);
        int clearValue = 0;
        // 后bitLength位为1的32位数
        for (int i = 0; i < bitLength; i++) {
            clearValue <<= 1;
            clearValue += 1;
        }
        // 把从bitIndex开始，长度bitLength的一段数字移到末尾，然后将该段数字以外的所有位全部清零
        return intValue >> bytes.length * 8 - (bitIndex + bitLength - 1) & clearValue;
    }

    // public static void main(String[] args) {
    // byte [] byte2= {1,1};
    // log.debug(getBitsValue(0, 9, byte2));
    //
    // }
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();

    }

    public static byte[] toStringHex(String hexString) {
        byte[] baKeyword = new byte[hexString.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(hexString.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
//                log.warn("", e);
            }
        }
        return baKeyword;
    }

    /**
     * @Description 字符串边字节数组 8421码
     */
    public static byte[] strToBcd(String asc) {
        int len = asc.length();
        int mod = len % 2;

        if (mod != 0) {
            asc = "0" + asc;
            len = asc.length();
        }

        byte abt[] = new byte[len];
        if (len >= 2) {
            len = len / 2;
        }

        byte bbt[] = new byte[len];
        abt = asc.getBytes();
        int j, k;

        for (int p = 0; p < asc.length() / 2; p++) {
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
                j = abt[2 * p] - '0';
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
                j = abt[2 * p] - 'a' + 0x0a;
            } else {
                j = abt[2 * p] - 'A' + 0x0a;
            }

            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
                k = abt[2 * p + 1] - '0';
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
                k = abt[2 * p + 1] - 'a' + 0x0a;
            } else {
                k = abt[2 * p + 1] - 'A' + 0x0a;
            }

            int a = (j << 4) + k;
            byte b = (byte) a;
            bbt[p] = b;
        }
        return bbt;
    }

    /**
     * @Description int类型转换成一个2长度的byte数组
     *
     */
    public static byte[] int2bytes2(int value) {
        byte[] ret = new byte[2];
        ret[1] = (byte) (value & 0xFF);// 低位
        value = value >> 8;
        ret[0] = (byte) (value & 0xFF);// 高位
        return ret;
    }

    /**
     * @Description int类型转换成一个4长度的byte数组
     *
     */
    public static byte[] int2bytes4(int value) {
        int temp = value;
        byte[] b = new byte[4];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Integer(temp & 0xff).byteValue();
            temp = temp >>> 8;
        }
        return b;
    }

    /**
     * @Description int类型转换成一个2-4长度的byte数组
     */
    public static byte[] intToByteArray(int value) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            int offset = (b.length - 1 - i) * 8;
            b[i] = (byte) ((value >>> offset) & 0xFF);
        }
        return b;
    }

    /**
     * @Description:字符串转化为标准日期格式
     * @param :字符串
     * @return：日期类型
     * @throws Exception
     */
    public static Date str2Date(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
        Date dates = null;
        try {
            dates = format.parse(dateStr);
        } catch (Exception e) {
//            log.warn("", e);
        }
        return dates;
    }

    // 字符串转换为ASCII
    public static String stringToAscii(String value) {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                sbu.append((int) chars[i]).append(",");
            } else {
                sbu.append((int) chars[i]);
            }
        }
        return sbu.toString();
    }

    /**
     * @Description:格式化dubble类型(主要用于里程计算)
     * @param :args
     * @return
     * @throws Exception
     */
    public static double formatMileageData(double mileage) {
        DecimalFormat df = new DecimalFormat("###.00");
        return Double.valueOf(df.format(mileage));
    }

    /**
     * @Description:整型转化为4个字节数据
     * @param :args
     * @return
     * @throws Exception
     */
    public final static byte[] Integer4HexBytes(int src, int bit) {
        byte[] yaIntDatas = new byte[4];
        for (int i = (bit - 1); i >= 0; i--) {
            yaIntDatas[(bit - 1) - i] = (byte) ((src >> 8 * i) & 0xff);
        }
        return yaIntDatas;
    }

    /**
     * @Description:整型转化为2个字节数据
     * @param :args
     * @return
     * @throws Exception
     */
    public final static byte[] Integer2HexBytes(int src, int bit) {
        byte[] yaIntDatas = new byte[2];
        for (int i = (bit - 1); i >= 0; i--) {
            yaIntDatas[(bit - 1) - i] = (byte) ((src >> 8 * i) & 0xff);
        }
        return yaIntDatas;
    }

    public static long strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate.getTime();
    }

    /**
     * @Description:请求位置格式化时间
     * @param :args
     * @return
     * @throws Exception
     */
    public static String toDateFormat(Date date) {
        // 时间
        // 格式化时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sTime = format.format(date);
        return sTime;
    }

    /**
     * int转化为两位十六进制的方法(具体看HQ_DOWN_AO如何使用的)
     *
     * @param :args
     * @return
     * @throws Exception
     */
    public static byte[] intToHex(int value) {
        byte[] resultbyte = new byte[2];

        String resulta = Integer.toHexString(value);
        while (resulta.length() < 4) {
            resulta = "0" + resulta;
        }
        resultbyte[0] = Byte.parseByte(resulta.substring(0, 2));
        resultbyte[1] = Byte.parseByte(resulta.substring(2));

        return resultbyte;
    }

    /**
     * int转化为两位十六进制字符串的方法
     *
     * @param :length 一个int类型的值
     * @return resulStr 代表16进制的一个字符串,长度为4 补零
     * @throws Exception
     */
    public static String getHexString(int length) {
        String resulStr = Integer.toHexString(length);
        while (resulStr.length() < 4) {
            resulStr = "0" + resulStr;
        }
        return resulStr;
    }

    /**
     * @Description:十进制转十六进制的方法
     * @param :args
     * @return
     * @throws Exception
     */
    public static String getIntToHex(int values) {
        String msg = Integer.toHexString(values);
        if (msg.length() == 1){
            msg = "000" + msg;
        }
        else if (msg.length() == 2){
            msg = "00" + msg;
        }
        else if (msg.length() == 3){
            msg = "0" + msg;
        }
        else{
            msg = "" + msg;
        }
        return msg;
    }

    /**
     * @Description:十进制转二进制的方法
     * @param :args
     * @return
     * @throws Exception
     */
    public static String getIntToBanary(int values) {
        String msg = Integer.toBinaryString(values);
        if (msg.length() == 1){
            msg = "000" + msg;
        }
        else if (msg.length() == 2)
        {
            msg = "00" + msg;
        }
        else if (msg.length() == 3)
        {
            msg = "0" + msg;
        }
        else{
            msg = "" + msg;
        }
        return msg;
    }

    /**
     * @Description:整型转为为32位二进制的方法
     * @param :args
     * @return
     * @throws Exception
     */
    public static String intTo32Binary(int n) {
        String str = "";
        // 移位操作
        for (int i = 0x80000000; i != 0; i >>>= 1) {
            str += (n & i) == 0 ? '0' : '1';
        }
        return str;
    }


}
