package com.example.android_get_message;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class Settings {
    private static Settings settings;
    private final SharedPreferences preferences;

    private String allowMessageFrom;
    private boolean displayMessageToast;

    private Settings(Context context) {
        preferences = context.getApplicationContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        allowMessageFrom = preferences.getString("allowMessageFrom", null);
        displayMessageToast = preferences.getBoolean("displayMessageToast", true);
    }

    public static Settings getInstance(Context context) {
        if (settings == null) {
            settings = new Settings(context);
        }
        return settings;
    }

    public static Settings getInstance() {
        return settings;
    }


    // Getter and Setter

    public String getAllowMessageFrom() {
        return allowMessageFrom;
    }

    public void setAllowMessageFrom(String allowMessageFrom) {
        this.allowMessageFrom = allowMessageFrom;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("allowMessageFrom", allowMessageFrom);
        editor.apply();
    }

    public boolean isDisplayMessageToast() {
        return displayMessageToast;
    }

    public void setDisplayMessageToast(boolean displayMessageToast) {
        this.displayMessageToast = displayMessageToast;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("displayMessageToast", displayMessageToast);
        editor.apply();
    }
}
