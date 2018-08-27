package com.gerrymatthewnick.randomsideproject.FingerFishing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.PREFERENCES_SOUND;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        CheckBox sound = findViewById(R.id.checkBoxSound);

        sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                SharedPreferences soundFile = getSharedPreferences(PREFERENCES_SOUND, MODE_PRIVATE);
                SharedPreferences.Editor editor = soundFile.edit();
                editor.putBoolean("soundOption", isChecked);
                editor.apply();
            }
        });
    }

    public void changeToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void changeToStats(View view) {
        Intent intent = new Intent(this, StatsActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        //prevent back press
    }

}
