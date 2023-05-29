package org.example.fxapplication.Security;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordEncoder {
    public static String encode(String password){
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }
    public static Boolean comparePasswords(String password, String hashedPassword){
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
        return result.verified;
    }
}
