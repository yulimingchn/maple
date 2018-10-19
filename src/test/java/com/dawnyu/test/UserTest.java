package com.dawnyu.test;

import org.springframework.util.DigestUtils;

public class UserTest {

    public static void main(String[] args) {
        System.out.println(DigestUtils.md5DigestAsHex("dawn1991".getBytes()));
    }


}
