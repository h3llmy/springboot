package com.testing.util;

import org.mindrot.jbcrypt.BCrypt;

public class Encryption {
    public static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static boolean match(String currentPassword, String comparedPassword) {
        return BCrypt.checkpw(comparedPassword, currentPassword);
    }
}