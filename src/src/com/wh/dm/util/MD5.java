
package com.wh.dm.util;

public class MD5 {

    // º”√‹À„∑®
    public static String encrytmd5(String pass) {

        char[] a = pass.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ '1');
        }
        String s = new String(a);
        return s;
    }

}
