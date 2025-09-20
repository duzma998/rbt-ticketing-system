package com.nikolastojanovic.rbtticketingsystem.domain.util;

import java.security.SecureRandom;

/**
 * Generator jedinstvenih kodova za karte
 * Generiše 12-karakterni alfanumerički kod u formatu XXXX-XXXX-XXXX
 */
public class CodeGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final int CODE_LENGTH = 12;

    /**
     * Generiše jedinstven 12-karakterni kod
     * Format: ABCD-EFGH-IJKL
     * Mogućih kombinacija: 36^12 = ~4.7 x 10^18
     *
     * @return String kod karte u formatu XXXX-XXXX-XXXX
     */
    public static String generateTicketCode() {
        StringBuilder code = new StringBuilder();

        // Generiši 12 random karaktera
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(SECURE_RANDOM.nextInt(CHARACTERS.length())));
        }

        // Formatiranje sa crticama za čitljivost
        String rawCode = code.toString();
        return rawCode.substring(0, 4) + "-" +
                rawCode.substring(4, 8) + "-" +
                rawCode.substring(8, 12);
    }

    /**
     * Validira format koda karte
     *
     * @param code kod za validaciju
     * @return true ako je kod u validnom formatu
     */
    public static boolean isValidFormat(String code) {
        if (code == null) {
            return false;
        }

        // Proveri format XXXX-XXXX-XXXX ili 12 karaktera bez crtice
        return code.matches("^[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}$");
    }
}
