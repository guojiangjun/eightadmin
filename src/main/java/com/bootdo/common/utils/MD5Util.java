package com.bootdo.common.utils;

import java.security.MessageDigest;

/**
 * Created by god on 2017/7/6.
 */
public class MD5Util {
    /**
     * Get the md5 of the given key.
     * 计算MD5值
     */
    public static String computeMd5(String k) {
        MessageDigest md5 = null;

        try {

            md5 = MessageDigest.getInstance("MD5");

        } catch (Exception e) {

            System.out.println(e.toString());
            e.printStackTrace();

            return "";
        }

        char[] charArray = k.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];

        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < md5Bytes.length; i++) {

            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }

    public static void main(String[] args) {
        System.out.println(MD5Util.computeMd5("hello"));
    }

}
