package com.nikolastojanovic.rbtticketingsystem.domain.util;

import java.security.SecureRandom;


public class CodeGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final int CODE_LENGTH = 12;

    public static String generateTicketCode() {
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(SECURE_RANDOM.nextInt(CHARACTERS.length())));
        }

        String rawCode = code.toString();
        return rawCode.substring(0, 4) + "-" +
                rawCode.substring(4, 8) + "-" +
                rawCode.substring(8, 12);
    }


    public static boolean isValidFormat(String code) {
        if (code == null) {
            return false;
        }

        return code.matches("^[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}$");
    }
}
