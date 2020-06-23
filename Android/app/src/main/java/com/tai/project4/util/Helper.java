package com.tai.project4.util;

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


}
