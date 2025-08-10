package com.rabbiter.hospital.utils;

import org.springframework.util.DigestUtils;

public class Md5Util {

    //盐，用于混交md5
    private static final String salt = "asdwqAsd12_qS";

    public static String getMD5(String str) {
        String base = str + "/" + salt;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }


}