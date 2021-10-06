package com.example.onlinephoneshop.utils;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Helper {
    public static String extractDateForPrefixId() {
        String pattern = "dd-MM-yyyy";
        String dateInString = new SimpleDateFormat(pattern).format(new Date());

        return dateInString.replace("-", "");
    }

    public static Boolean verifyPhoneNumber(String phoneNumber) {
        char[] chars = phoneNumber.toCharArray();
        for (char c : chars) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static String generatePassword(int length) {
        byte[] array = new byte[256];
        new Random().nextBytes(array);

        String randomString = new String(array, Charset.forName("UTF-8"));
        // Create a StringBuffer to store the result
        StringBuffer r = new StringBuffer();
        // Append first 20 alphanumeric characters
        // from the generated random String into the result
        for (int k = 0; k < randomString.length(); k++) {
            char ch = randomString.charAt(k);
            if (((ch >= 'a' && ch <= 'z')
                    || (ch >= 'A' && ch <= 'Z')
                    || (ch >= '0' && ch <= '9'))
                    && (length > 0)) {
                r.append(ch);
                length--;
            }
        }
        // return the resultant string
        return r.toString();
    }
}
