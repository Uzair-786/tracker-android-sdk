package com.example.loginapp;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.UUID;


public class SessionManager {

    //initialize variables
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String uniqueNumber ;

    //Create constructor
    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences("AppKey", 0);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    //Create set login method
    public void setLogin(Boolean login) {
        editor.putBoolean("KEY_LOGIN", login);
        editor.commit();
    }

    //Create get login method
    public boolean getLogin() {
        return sharedPreferences.getBoolean("KEY_LOGIN", false);
    }

    //Create set username method
    public void setUsername(String username) {
        editor.putString("KEY_USERNAME", username);
        editor.commit();
    }

    //Create get username method
    public String getUsername() {
        return sharedPreferences.getString("KEY_USERNAME", "");
    }

    public String getSessionId(){

            UUID number = UUID.randomUUID();
           uniqueNumber =  number.toString();
            return "SessionId :" + uniqueNumber;

    }

}
