package uci.cs297p.common;

import java.security.MessageDigest;

public class MD5Util {
    public static void main(String[] args) {
        String pwd = getMD5("admin");
        System.out.println(pwd);
    }

    // Create MD5
    public static String getMD5(String message) {
        String md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");  // Create one object of MD5 algorithm
            byte[] messageByte = message.getBytes("UTF-8");
            byte[] md5Byte = md.digest(messageByte);              // Acquire MD5 byte array, 16*8=128 bits
            md5 = bytesToHex(md5Byte);                            // transfer to hexadecimal String
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5;
    }

    // Binary to hexadecimal
    public static String bytesToHex(byte[] bytes) {
        StringBuffer hexStr = new StringBuffer();
        int num;
        for (int i = 0; i < bytes.length; i++) {
            num = bytes[i];
            if (num < 0) {
                num += 256;
            }
            if (num < 16) {
                hexStr.append("0");
            }
            hexStr.append(Integer.toHexString(num));
        }
        return hexStr.toString().toUpperCase();
    }
}
