/**
 * ---------------------------------
 * Copyright (c) 2016
 * RESOLVE Software Research Group
 * School of Computing
 * Clemson University
 * All rights reserved.
 * ---------------------------------
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package models.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * <p>This class contains different utility methods for the various
 * different model classes.</p>
 *
 * @author Yu-Shan Sun
 * @version 1.0
 */
public class ModelUtilities {

    // ===========================================================
    // Public Methods
    // ===========================================================

    /**
     * <p>Encrypts the user password.</p>
     *
     * @param password The un-encrypted user password.
     *
     * @return A hashed version of the password.
     */
    public static String encryptPassword(String password) {
        return stringHashing(password);
    }

    /**
     * <p>Generates a confirmation code using
     * the specified strings.</p>
     *
     * @param str1 String 1.
     * @param str2 String 2.
     * @param str3 String 3.
     * @param str4 String 4.
     *
     * @return An unique confirmation code.
     */
    public static String generateConfirmationCode(String str1, String str2,
            String str3, String str4) {
        String randomID = UUID.randomUUID().toString();
        return stringHashing(str1 + str2 + str3 + str4 + randomID);
    }

    // ===========================================================
    // Private Methods
    // ===========================================================

    /**
     * <p>This method uses SHA-256 hashing to encrypt the
     * string passed in.</p>
     *
     * @param originalString This is the string we want to hash.
     *
     * @return Hashed string.
     */
    private static String stringHashing(String originalString) {
        StringBuilder sb = new StringBuilder();

        try {
            // Obtain SHA-256 hash and apply it to originalString
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(originalString.getBytes());
            byte byteData[] = md.digest();

            // Convert the byte to hex format method 1
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
        }
        catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }

        return sb.toString();
    }

}