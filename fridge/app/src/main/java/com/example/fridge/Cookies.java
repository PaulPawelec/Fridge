package com.example.fridge;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Cookies {
    private DateFormat date;

    public Cookies(){
        date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    }

    public void setCookies(String data){
        String dateString = data.substring(2);
    }

    public Boolean isValid(){
        final  String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse("date");
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
