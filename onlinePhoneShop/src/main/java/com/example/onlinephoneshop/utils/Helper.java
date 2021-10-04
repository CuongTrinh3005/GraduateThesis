package com.example.onlinephoneshop.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
    public static String extractDateForPrefixId(){
        String pattern = "dd-MM-yyyy";
        String dateInString =new SimpleDateFormat(pattern).format(new Date());

        return dateInString.replace("-","");
    }

    public static Boolean verifyPhoneNumber(String phoneNumber){
        char[] chars = phoneNumber.toCharArray();
        for(char c : chars){
            if(!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }
}
