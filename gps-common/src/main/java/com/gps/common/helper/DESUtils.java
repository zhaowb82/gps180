package com.gps.common.helper;

//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * @author zhangsy
 * @create 2016-11-15 14:18
 */
public class DESUtils {
    // 加密
    public static String encrypt(String src, String key) {
        try {
            DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKey securekey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, securekey);
            byte[] b = cipher.doFinal(src.getBytes("UTF-8"));
//            BASE64Encoder encoder = new BASE64Encoder();
//            return encoder.encode(b);
            Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 解密
    public static String decrypt(String src, String key) {
        try {
// --通过base64,将字符串转成byte 数组
//            BASE64Decoder decoder = new BASE64Decoder();
//            byte[] bytesrc = decoder.decodeBuffer(src);
            Decoder decoder = Base64.getDecoder();
            byte[] bytesrc = decoder.decode(src);
// --解密的key
            DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKey securekey = keyFactory.generateSecret(dks);
// --Chipher 对象解密
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.DECRYPT_MODE, securekey);
            byte[] retByte = cipher.doFinal(bytesrc);
            return new String(retByte, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}