package com.gerrymatthewnick.randomsideproject.FingerFishing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.PREFERENCES_CHERRY_COUNT;

public class StatsActivity extends AppCompatActivity {

    private TextView cherryCountDisplay;
    private SharedPreferences cherryCountFile;
    private int currentCherryCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        setCherryDisplay();
    }

    private void setCherryDisplay() {
        cherryCountFile = getSharedPreferences(PREFERENCES_CHERRY_COUNT, MODE_PRIVATE);
        currentCherryCount = cherryCountFile.getInt("cherries", 0);

        cherryCountDisplay = findViewById(R.id.cherryCountDisplay);
        cherryCountDisplay.setText("Number of cherries collected: " + currentCherryCount);
    }

    public void changeToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        //prevent back press
    }
}
