package com.megalotto.megalotto.utils;


public class Base64 {
    static final char[] charTab = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

    public static String encode(byte[] data) {
        return encode(data, 0, data.length, null).toString();
    }

    public static StringBuffer encode(byte[] data, int start, int len, StringBuffer buf) {
        if (buf == null) {
            buf = new StringBuffer((data.length * 3) / 2);
        }
        int end = len - 3;
        int i = start;
        int n = 0;
        while (i <= end) {
            int d = ((data[i] & 255) << 16) | ((data[i + 1] & 255) << 8) | (data[i + 2] & 255);
            char[] cArr = charTab;
            buf.append(cArr[(d >> 18) & 63]);
            buf.append(cArr[(d >> 12) & 63]);
            buf.append(cArr[(d >> 6) & 63]);
            buf.append(cArr[d & 63]);
            i += 3;
            int n2 = n + 1;
            if (n < 14) {
                n = n2;
            } else {
                n = 0;
                buf.append("\r\n");
            }
        }
        if (i == (start + len) - 2) {
            int d2 = ((data[i] & 255) << 16) | ((data[i + 1] & 255) << 8);
            char[] cArr2 = charTab;
            buf.append(cArr2[(d2 >> 18) & 63]);
            buf.append(cArr2[(d2 >> 12) & 63]);
            buf.append(cArr2[(d2 >> 6) & 63]);
            buf.append("=");
        } else if (i == (start + len) - 1) {
            int d3 = (data[i] & 255) << 16;
            char[] cArr3 = charTab;
            buf.append(cArr3[(d3 >> 18) & 63]);
            buf.append(cArr3[(d3 >> 12) & 63]);
            buf.append("==");
        }
        return buf;
    }
}
