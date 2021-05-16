package com.example.android_get_message;

import android.content.Context;
import android.content.SharedPreferences;

public class Settings {
    private final SharedPreferences preferences;

    private String allowMessageFrom;
    private boolean displayMessageToast;

    public Settings(Context context) {
        preferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        allowMessageFrom = preferences.getString("allowMessageFrom", null);
        displayMessageToast = preferences.getBoolean("displayMessageToast", true);
    }

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
