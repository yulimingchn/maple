package com.dawn.maple.test;

import com.dawn.maple.utils.ValidateCodeUtil;

/**
 * @author dawn
 */
public class ValidateCodeTest {

    public static void main(String[] args) {

        ValidateCodeUtil.Validate v = ValidateCodeUtil.getRandomCode();
        System.out.println(v.getBase64Str());
        System.out.println(v.getValue());
    }




}
