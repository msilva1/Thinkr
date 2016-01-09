package com.bytes.thinkr.service.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Kent on 1/8/2016.
 */
public class PasswordUtil {

    /**
     * The password encryption algorithm
     * One-way has: MD5
     * -- http://www.ietf.org/rfc/rfc1321.txt
     * Two-way hash: SHA-1
     * -- http://csrc.nist.gov/publications/PubsFIPS.html SHA-256 SHA-384 SHA-512
     */
    private static final String PWD_ENCRYPT_ALG = "MD5";

    private static final Logger LOGGER = Logger.getLogger(PasswordUtil.class.getName());

    /**
     * Encrypt the plain password using a message digest algorithm (MD5)
     *
     * Note:
     * Validate the plain password before calling this method.
     * This is a one-way hash
     * TODO Add salt and iterations
     *
     * @param password the password to be hashed, NOT nullable
     * @return the hashed password.
     */
    public static String encryptPassword(String password) {

        StringBuilder pwd = new StringBuilder();
        try {

            byte[] encryptedPwd = MessageDigest.getInstance(PWD_ENCRYPT_ALG).digest(password.getBytes());

            // Convert byte to Hex
            for (byte b : encryptedPwd)
                pwd.append(Integer.toString((b & 0xFF), 16));

        } catch (NoSuchAlgorithmException e) {

            LOGGER.log(Level.SEVERE, "Unable to encrypt password using: " + PWD_ENCRYPT_ALG, e);
        }

        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Plain password: " + password + " digest: " + pwd.toString());
        }

        return pwd.toString();
    }
}
