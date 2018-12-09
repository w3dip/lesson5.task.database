package ru.sberbank.lesson5.task.database;

import android.app.Activity;
import android.os.Bundle;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        findViewById(R.id.saveSettingsBtn).setOnClickListener((v) -> {
            finish();
        });
    }
}
