package com.nuasolutions.todomanagement.utils;

import java.util.regex.Pattern;

public class CommonUtils {
    public static boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static boolean validatePassword(String password) {
        if (password == null || password.length() < 6)
            return false;
        return true;
    }
}
