package com.event.ValidateData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUserData {
    public static boolean validateId(int id) {
        if (id > 0 && id < 1000000)
            return true;
        else
            return false;
    }

    public static boolean validateName(String name) {
        Pattern pattern = Pattern.compile("[^a-z]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);

        if (matcher.find())
            return false;
        else
            return true;
    }

}


