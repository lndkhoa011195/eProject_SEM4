package com.group1.project4.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static boolean validateLetters(String txt) {

        String regx = "^[a-zA-Z ]+$";
        Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(txt);
        return matcher.find();

    }

    public static boolean validatePhone(String txt) {

        String regx = "[0-9]{10,}";
        Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(txt);
        return matcher.find();

    }

    public static boolean validateAddress(String txt) {

        String regx = ".{0,200}";
        Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(txt);
        return matcher.find();

    }
}
