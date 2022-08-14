package com.example.fridge;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class LoginSessions{
    static LoginSessions instance = null;
    public static LoginSessions getInstance() {
        if (instance == null) {
            instance = new LoginSessions();
        }
        return instance;
    }

    public String session = "";

    public String getSession() {
        System.out.println("getsession: "+session);
        return session;
    }
    public void setSession(String session) {
        this.session = session;
    }
}