package com.example.android_get_message;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        private Settings settings;

        private EditTextPreference allowMessageFromItem;
        private SwitchPreferenceCompat displayMessageToastItem;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            settings = Settings.getInstance();

            preferenceInit();
        }

        private void preferenceInit() {
            allowMessageFromItem = findPreference("allowMessageFrom");
            if (allowMessageFromItem != null) {
                allowMessageFromItem.setText(settings.getAllowMessageFrom());
                allowMessageFromItem.setOnPreferenceChangeListener((preference, newValue) -> {
                    System.out.println("allowMessageFrom = " + newValue);
                    settings.setAllowMessageFrom((String) newValue);
                    return true;
                });
            }

            displayMessageToastItem = findPreference("displayMessageToast");
            if (displayMessageToastItem != null) {
                displayMessageToastItem.setChecked(settings.isDisplayMessageToast());
                displayMessageToastItem.setOnPreferenceChangeListener((preference, newValue) -> {
                    System.out.println("displayMessageToast = " + newValue);
                    settings.setDisplayMessageToast((boolean) newValue);
                    return true;
                });
            }
        }
    }



//    @Override
//    public void onBackPressed() {
//        finish();
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}