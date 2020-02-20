package com.gps.common.helper;

import java.math.BigDecimal;
import java.util.Calendar;

public class ByteUtil {

    /**
     * 低位在前的4字节数组转化成整数
     *
     * @param src
     * @return
     */
    public static int byte2Int(byte[] src) {
        int ret = 0;
        for (int i = src.length - 1; i >= 0; i--) {
            ret = (ret << 8) | (src[i] & 0xFF);
        }
        return ret;
    }

    /**
     * 整型转化为4字节数组低位在前
     *
     * @param src
     * @return
     */
    public static byte[] int2Byte(int src) {
        byte[] bs = new byte[4];
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (byte) ((src >> 8 * i) & 0xFF);
        }
        return bs;
    }

    /**
     * 长整型转化为8字节数组低位在前
     *
     * @param src
     * @return
     */
    public static byte[] long2Byte(long src) {
        byte[] bs = new byte[8];
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (byte) ((src >> 8 * i) & 0xFF);
        }
        return bs;
    }

    /**
     * 整型转化为二进制数组
     *
     * @param src 整型变量，可以是byte,short,int
     * @param bytesLen 共取几个字节
     * @param littleEndian 是否低位在前
     * @return
     */
    public static byte[] integer2Bytes(int src, int bytesLen, boolean littleEndian) {
        byte[] bytes = new byte[bytesLen]; // 根据给定长度分配对应大小空间二进制数组
        if (littleEndian) { // 如果低位在前
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) ((src >> 8 * i) & 0xFF); // 末位清0还是啥的
            }
        } else { // 如果低位不在前
            int j = 0;
            for (int i = bytes.length - 1; i >= 0 && j < bytes.length; i--, j++) {
                bytes[i] = (byte) ((src >> 8 * j) & 0xFF); // 末位清0
            }
        }
        return bytes;
    }

    /**
     * 二进制数组转化成整型
     *
     * @param bs 原数组
     * @param littleEndian 是否低位在前
     * @return 整型
     */
    public static int bytes2Integer(byte[] bs, boolean littleEndian) {
        int ret = 0;
        if (!littleEndian) { // 如果低位不在前
            for (int i = 0; i < bs.length; i++) {
                ret = ret << 8 | (bs[i] & 0xFF);
            }
        } else {
            for (int i = bs.length - 1; i >= 0; i--) {
                ret = ret << 8 | (bs[i] & 0xFF);
            }
        }
        return ret;
    }

    /**
     * 二进制数组按指定下标数进行转换（统一转强转成Int） 因为最长不会超过四个字节数组
     *
     * @param bs 指导定数组
     * @param offset 开始下标
     * @param end 结束下标
     * @param littleEndian 是否低位在前
     * @return 转换完成的数
     */
    public static int bytesToIntegerAccordNumber(byte[] bs, int offset, int end, boolean littleEndian) {
        int ret = 0;
        if (!littleEndian) {
            for (int i = offset; i <= end; i++) {
                ret = ret << 8 | (bs[i] & 0xFF);
            }
        } else {

            for (int i = end; i >= offset; i--) {
                ret = ret << 8 | (bs[i] & 0xFF);
            }
        }
        return ret;
    }

    // public static void main(String[] args) {
    // int test = 1234;
    // log.debug(byte2Int(int2Byte(test)));
    // test = 22234;
    // byte[] bs = integer2Bytes(test, 4, true);
    // log.debug(bytes2Integer(bs, true));
    // }

    /**
     * @Description short类型转换为byte类型
     * @param source
     * @return return_type
     * @throws Exception
     */
    public static byte[] shortToByte(short source) {
        int temp = source;
        if (temp < 0) {
            temp += 65536;
        }
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Integer(temp & 0xff).byteValue();
            temp = temp >>> 8;
        }
        return b;
    }

    /**
     * @Description short转换为byteBig类型
     * @param source
     * @return return_type
     * @throws Exception
     */
    public static byte[] shortToByteBig(short source) {
        int temp = source;
        if (temp < 0) {
            temp += 65536;
        }
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++) {
            b[1 - i] = new Integer(temp & 0xff).byteValue();
            temp = temp >>> 8;
        }
        return b;
    }

    public static byte[] shortToByte(int source) {
        int tmp = source;
        if (tmp < 0) {
            tmp += 65536;
        }
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) (tmp & 0xff);
            tmp = tmp >>> 8;
        }
        return b;
    }

    public static byte[] shortToByteN(int source) {
        int tmp = source;
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Integer(tmp & 0xff).byteValue();
            tmp = tmp >>> 8;
        }
        return b;
    }

    public static byte[] intToByte(int source) {
        int temp = source;
        byte[] b = new byte[4];
        for (int i = 0; i > b.length; i++) {
            b[i] = new Integer(temp & 0xff).byteValue();
            temp = temp >>> 8;
        }
        return b;
    }

    public static byte[] intToByte2(int source) {
        int temp = source;
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) (temp & 0xff);
            temp = temp >>> 8;
        }
        return b;
    }

    public static byte[] intToByte3(int source) {
        int temp = source;
        byte[] b = new byte[3];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Integer(temp & 0xff).byteValue();
            temp = temp >>> 8;
        }
        return b;
    }

    public static byte[] intToByte4(int source) {
        int temp = source;
        byte[] b = new byte[4];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Integer(temp & 0xff).byteValue();
            temp = temp >>> 8;
        }
        return b;
    }

    public static int byteToShort(byte[] source) {
        int tmp = 0;
        int[] tmpArray = new int[2];
        if (source.length != 2) {
            return -1;
        }
        for (int i = 0; i < source.length; i++) {
            if (source[i] < 0) {
                tmpArray[i] = source[i] + 256;
            } else {
                tmpArray[i] = source[i];
            }
        }
        tmp = (tmpArray[1] << 8) + tmpArray[0];
        return tmp;
    }

    final public static int byte3ToInt(byte[] source) {
        int[] tmpArray = new int[3];
        // int lowPart=0;
        if (source.length != 3) {
            return -1;
        }
        for (int i = 0; i < source.length; i++) {
            if (source[i] < 0) {
                tmpArray[i] = source[i] + 256;
            } else {
                tmpArray[i] = source[i];
            }
        }
        int tmp2 = ((tmpArray[2] << 16)) + ((tmpArray[1] << 8)) + tmpArray[0];// litten-endian
        // convert
        return tmp2;
    }

    final public static int byte4ToInt(byte[] source) {
        int[] tmpArray = new int[4];
        // int lowPart=0;
        if (source.length != 4) {
            return -1;
        }
        for (int i = 0; i < source.length; i++) {
            if (source[i] < 0) {
                tmpArray[i] = source[i] + 256;
            } else {
                tmpArray[i] = source[i];
            }
        }
        int tmp2 = ((tmpArray[3] << 24)) + ((tmpArray[2] << 16)) + ((tmpArray[1] << 8)) + tmpArray[0];
        return tmp2;
    }

    final public static int byte2ToInt(byte[] source) {
        int[] tmpArray = new int[2];
        if (source.length != 2) {
            return -1;
        }
        for (int i = 0; i < source.length; i++) {
            if (source[i] < 0) {
                tmpArray[i] = source[i] + 256;
            } else {
                tmpArray[i] = source[i];
            }
        }
        int tmp2 = ((tmpArray[1] << 8)) + tmpArray[0];
        return tmp2;
    }

    public final static long byte5ToLong(byte[] source) {
        int[] tmpArray = new int[5];
        if (source.length != 5) {
            return -1;
        }
        for (int i = 0; i < source.length; i++) {
            if (source[i] < 0) {
                tmpArray[i] = source[i] + 256;
            } else {
                tmpArray[i] = source[i];
            }
        }
        int tmp2 = (tmpArray[4] << 32) + (tmpArray[3] << 24) + (tmpArray[2] << 16) + (tmpArray[1] << 8) + tmpArray[0]; // litten-endian
        // convert
        return tmp2;
    }

    public static int seqConvertInt(int source) {
        int tmp = source;
        if (source < 0) {
            tmp = source + 65536;
        }
        int high = tmp >>> 8;
        int low = tmp & 0xff;
        int out = ((low << 8) + high);
        return out;
    }

    public static byte[] fromTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int date = calendar.get(Calendar.DATE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int mintue = calendar.get(Calendar.MINUTE);
        int compact = date + (hour << 5) + (mintue << 10);
        byte[] tmp = new byte[2];
        tmp[0] = (byte) (compact & 0xff);
        tmp[1] = (byte) (compact >>> 8);
        return tmp;
    }

    public static byte[] getBCDcode(long data)// reversal sequence
    {
        int seq = 0;
        byte[] tmp = new byte[20];
        while (data >= 1 && seq < 20) {
            byte t = (byte) (data % 10);
            tmp[seq++] = t;

            data = data / 10;
        }
        byte[] tmp2 = new byte[seq];
        System.arraycopy(tmp, 0, tmp2, 0, seq);
		/*
		 * log.debug("the length is " + tmp2.length);
		 *
		 * for (int i = 0; i < tmp2.length; i++) { log.debug("temp [ " + i + " ]" + tmp2[i]); }
		 */
        // 1 .get the length
        int length2 = (seq / 2) + 1;
        byte[] result = new byte[length2];
        // ---then compact all info
        if (seq % 2 != 0)// less a half byte
        {
            // 2 .compact info
            int k = -2;

            for (int i = 0; i < length2; i++) {
                k += 2;// k is couple

                if (k == (tmp2.length - 1))// out of bound ,need added a half
                // byte
                {
                    result[i] = (byte) (tmp2[k] | (0xf << 4));
                } else {
                    result[i] = (byte) (tmp2[k] | (tmp2[k + 1] << 4));
                }

            }// for(int i=0;i<length2;i++)

        }// if (seq%2 !=0)
        else { // less a end byte

            int k = -2;
            for (int i = 0; i < length2; i++) {
                k += 2;// k is couple
                if (k > (tmp2.length - 1))// out of bound ,need added a half
                // byte
                {
                    result[i] = (byte) 0xff;
                } else {
                    result[i] = (byte) (tmp2[k] | (tmp2[k + 1] << 4));
                }

            }// for(int i=0;i<length2;i++)
        } // else{ //less a end byte
        return result;
    }

    public static byte[] getBCDcode2(String data) {
        int length = data.length();
        int outLength = (length / 2) + 1;
        byte[] result = new byte[outLength];
        if (length % 2 == 0)// need a byte 0xff
        {
            int k = -2;
            for (int j = 0; j < outLength - 1; j++) {
                k += 2;
                result[j] = (byte) (((data.charAt(k) - '0') << 4) | (data.charAt(k + 1) - '0'));
            }
            result[outLength - 1] = (byte) 0xff;
        }// if(outLength%2 ==0)
        else
        // need a half byte 0xf
        {
            int k = -2;
            for (int j = 0; j < outLength - 1; j++) {
                k += 2;
                result[j] = (byte) (((data.charAt(k) - '0') << 4) | (data.charAt(k + 1) - '0'));
            }
            result[outLength - 1] = (byte) ((data.charAt(length - 1) << 4) | 0xf);
        }
        // for(int i=0)
        return result;
    }

    public static byte[] shortToBCD(short src) {
        String st = Short.toString(src);
        if (st.length() % 2 != 0)
            st = '0' + st; // ==add 0 to head of string if the length is not
        // even==
        byte[] result = new byte[st.length() / 2];
        for (int i = 0; i < result.length; i++) {
            String s = st.substring(2 * i, 2 * (i + 1));
            result[i] = Byte.parseByte(s, 16);
        }
        return result;
    }

    public static byte[] intToBCD(int src) {
        String st = Integer.toString(src);
        if (st.length() % 2 != 0)
            st = '0' + st;
        byte[] result = new byte[st.length() / 2];
        for (int i = 0; i < result.length; i++) {
            String s = st.substring(2 * i, 2 * (i + 1));
            result[i] = (byte) Integer.parseInt(s, 16);
        }
        return result;
    }

    public final static int bytes2BCD(byte[] bs) {
        int bcd = 0;
        for (int i = 0; i < bs.length; i++) {
            byte e = bs[i];
            bcd = (bcd * 10) + (e & 0x0f);
            bcd = (bcd * 10) + (e & 0xf0) >> 4;
        }
        return bcd;
    }

    // ascii码字节转化成16进制字符串。
    // public final static String bytes2BCDStr(byte[] bs) {
    // String str = "";
    // for (int i = 0; i < bs.length; i++) {
    // byte e = bs[i];
    // str += Integer.toHexString((e >> 4) & 0x0f);
    // str += Integer.toHexString(e & 0x0f);
    // }
    // return str;
    // }
    // 字节数组转化成16进制字符串,一个字节转化成两个16进制数，对于上传的就是16进制的字节数组来说，将转化为0？0？0？这样的形式，对于上传上来的是asc字节数组来说，将转化为16进制的字节数组。
    public final static String bytes2BCDStr(byte[] src) {
        if (src == null || src.length <= 0) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < src.length; i++) {
            byte e = src[i];
            sb.append(Integer.toHexString((e >> 4) & 0x0f));
            sb.append(Integer.toHexString(e & 0x0f));
        }
        return sb.toString();
    }

    public final static String Integer2HexString(int src, int bit) {
        StringBuffer sb = new StringBuffer();
        for (int i = (bit - 1); i >= 0; i--) {
            sb.append(Integer.toHexString((src >> 4 * i) & 0x0f));
        }
        // sb.append(Integer.toHexString(src & 0x0f));
        return sb.toString();
    }

    public final static byte[] Integer2HexBytes(int src, int bit) {
        byte[] yaIntDatas = new byte[4];
        for (int i = (bit - 1); i >= 0; i--) {
            yaIntDatas[(bit - 1) - i] = (byte) ((src >> 8 * i) & 0xff);
        }
        return yaIntDatas;
    }

	/*
	 * public final static byte[] Integer2HexBytes(int src, int bit) { // StringBuffer sb = new StringBuffer(); byte[] yaIntDatas = new byte[4]; for
	 * (int i = (bit - 1); i >= 0; i--) { // sb.append(Integer.toHexString((src >> 8 * i) & 0xff));//1.打印2.存储这个暂时的整形int移位后的变量。
	 * log.debug("Integer.toHexString((src >> 8 * i) & 0xff) = " + Integer.toHexString((src >> 8 * i) & 0xff)); yaIntDatas[(bit - 1) - i] =
	 * (byte) ((src >> 8 * i) & 0xff); } //1.利用byte截取int的低位一字节数据2.利用暂时存储这个整形int移位后的变量的方法来实现int-->到4字节(byte)的转换。 // int nTemp = src; // for (int i =
	 * (bit - 1); i >= 0; i--) { // nTemp = (nTemp >> 8 * i) & 0xff; // log.debug("Integer.toHexString(nTemp) = " +
	 * Integer.toHexString(nTemp)); // yaIntDatas[(bit - 1) - i] = (byte) nTemp; // } // sb.append(Integer.toHexString(src & 0x0f)); return
	 * yaIntDatas; // return sb.toString(); }
	 */
    // public final static String Integer2HexString(int src) {
    // StringBuffer sb = new StringBuffer();
    // sb.append(Integer.toHexString((src >> 4) & 0x0f));
    // sb.append(Integer.toHexString(src & 0x0f));
    // return sb.toString();
    // }
    // public final static String Short2HexString(Short src) {
    // StringBuffer sb = new StringBuffer();
    // sb.append(Integer.toHexString((src >> 8) & 0x00ff));
    // sb.append(Integer.toHexString(src & 0x00ff));
    // return sb.toString();
    // }

    /**
     * 将字节数组转换为bcd码的String形式
     *
     * @param bs 指导定数组
     * @param offset 开始下标
     * @param end 结束下标
     * @param littleEndian 是否低位在前
     * @return 转换完成的数
     */
    public final static String bytes2BcdStrAccordNumber(byte[] bs, int offset, int end, boolean littleEndian) {
        String str = "";
        if (!littleEndian) {
            for (int i = offset; i <= end; i++) {
                byte e = bs[i];
                str += Integer.toHexString((e >> 4) & 0x0f);
                str += Integer.toHexString(e & 0x0f);
            }
        } else {
            for (int i = end; i >= end; i--) {
                byte e = bs[i];
                str += Integer.toHexString((e >> 4) & 0x0f);
                str += Integer.toHexString(e & 0x0f);
            }
        }
        return str;
    }

    public final static String getASCIIHexStr(byte[] bs, int index, int len) {
        StringBuffer ascii = new StringBuffer();
        if (bs == null || index > bs.length || index < 0 || len <= 0 || index + len > bs.length) {
            return ascii.toString();
        }
        for (int i = 0; i < len; i++) {
            ascii.append((char) (Integer.parseInt(bs[i + index] + "", 16)));
        }
        return ascii.toString();
    }

    public final static String getASCIIStr(byte[] bs, int index, int len) {
        StringBuffer ascii = new StringBuffer();
        if (bs == null || index > bs.length || index < 0 || len <= 0 || index + len > bs.length) {
            return ascii.toString();
        }
        for (int i = 0; i < len; i++) {
            ascii.append((char) bs[i + index]);
        }
        return ascii.toString();
    }

    /**
     * 精度转换，精确到小数点后6位，当最后位为0时则不显示0
     *
     * @param t 小数部分，如0.23023923239023 当小数点后不满6位时则补0，使其包含"0."的整个长度至少是8位。
     * @param scale : 精确到小数点后几位
     * @return double 返回精确到小数点后6位double类型数据
     */
    public static double convertDecimal(double t, int scale) {
        String str = Double.toString(t);
        while (str.length() < 8) {
            str += "0";
        }
        str = str.substring(0, scale);
        double tmp = Double.parseDouble(str);
        return tmp;
    }

    /**
     * 小数后保留几位
     *
     * @param value 原数字
     * @param scale 小数位数
     * @return
     */
    public static double convertDouble(double value, int scale) {
        BigDecimal b = new BigDecimal(Double.toString(value));
        BigDecimal one = new BigDecimal("1");
        double div = b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        return checkDoubleLength(div);
    }

    public static double checkDoubleLength(double div) {
        // String temp;
        String divStr = new Double(div).toString();
        if (divStr.length() > 10) {
            divStr = divStr.substring(0, 10);
            div = Double.parseDouble(divStr);
        }
        return div;
    }

    /**
     * 将float转为低字节在前，高字节在后的byte数组
     */
    public static byte[] floatToByte(float f) {
        int temp = Float.floatToRawIntBits(f);
        return int2Byte(temp);

    }

    /**
     * 将double转为低字节在前，高字节在后的byte数组
     */
    public static byte[] doubleToByte(double d) {
        long temp = Double.doubleToRawLongBits(d);
        return long2Byte(temp);

    }

    /**
     * 将16进制字符串转为字符串
     *
     * @param hexString 欲转换的16进制字符串
     */
    public static String toStringHex(String hexString) {
        byte[] baKeyword = new byte[hexString.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(hexString.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
//                log.warn("", e);
            }
        }

        try {
            hexString = new String(baKeyword, "utf-8");// UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return hexString;
    }

    /**
     * 将有符号整数转为无符号整数(单字节)
     *
     * @param src
     * @return int
     */
    public final static int toUnsignInt(int src) {
        if (src < 0) {
            return src + 256;
        } else {
            return src;
        }
    }

    public final static byte toUngisnByte(byte src) {
        int byteValue;
        int temp = src % 256;
        if (src < 0) {
            byteValue = temp < -128 ? 256 + temp : temp;
        } else {
            byteValue = temp > 127 ? temp - 256 : temp;
        }
        return (byte) byteValue;
    }
}
