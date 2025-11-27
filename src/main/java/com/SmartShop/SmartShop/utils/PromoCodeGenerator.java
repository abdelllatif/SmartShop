package com.SmartShop.SmartShop.utils;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.security.SecureRandom;

public class PromoCodeGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random= new SecureRandom();
    public static String generatePromoCode(int lenght) {
        StringBuffer sb =new StringBuffer((lenght));
        for (int i = 0; i < lenght; i++) sb.append((CHARACTERS.charAt(random.nextInt(CHARACTERS.length()))));
        return sb.toString();
    }
}
