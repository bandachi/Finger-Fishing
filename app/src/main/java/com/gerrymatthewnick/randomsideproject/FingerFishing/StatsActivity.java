package com.gerrymatthewnick.randomsideproject.FingerFishing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.PREFERENCES_CHERRY_COUNT;
import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.PREFERENCES_COIN_COUNT;
import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.PREFERENCES_HIGH_LEVEL;
import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.PREFERENCES_TIME;
import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.PREFERENCES_WORM_COUNT;

public class StatsActivity extends AppCompatActivity {

    private TextView cherryCountDisplay;
    private SharedPreferences cherryCountFile;
    private int currentCherryCount;

    private TextView wormCountDisplay;
    private SharedPreferences wormCountFile;
    private int currentWormCount;

    private TextView coinCountDisplay;
    private SharedPreferences coinCountFile;
    private int currentCoinCount;

    private TextView highLevelDisplay;
    private SharedPreferences highLevelFile;
    private int currentHighLevel;

    private TextView timeDisplay;
    private SharedPreferences highTimeFile;
    private float currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        setCherryDisplay();
        setWormDisplay();
        setCoinDisplay();
        setLevelDisplay();
        setTimeDisplay();
    }

    private void setCherryDisplay() {
        cherryCountFile = getSharedPreferences(PREFERENCES_CHERRY_COUNT, MODE_PRIVATE);
        currentCherryCount = cherryCountFile.getInt("cherries", 0);

        cherryCountDisplay = findViewById(R.id.cherryCountDisplay);
        cherryCountDisplay.setText("Total Number of cherries collected: " + currentCherryCount);
    }

    private void setWormDisplay() {
        wormCountFile = getSharedPreferences(PREFERENCES_WORM_COUNT, MODE_PRIVATE);
        currentWormCount = wormCountFile.getInt("worms", 0);

        wormCountDisplay = findViewById(R.id.wormCountDisplay);
        wormCountDisplay.setText("Total Number of worms collected: " + currentWormCount);
    }

    public void setCoinDisplay() {
        coinCountFile = getSharedPreferences(PREFERENCES_COIN_COUNT, MODE_PRIVATE);
        currentCoinCount = coinCountFile.getInt("coins", 0);

        coinCountDisplay = findViewById(R.id.coinCountDisplay);
        coinCountDisplay.setText("Total Number of coins collected: " + currentCoinCount);
    }

    public void setLevelDisplay() {
        highLevelFile = getSharedPreferences(PREFERENCES_HIGH_LEVEL, MODE_PRIVATE);
        currentHighLevel = highLevelFile.getInt("highestLevel", 0);

        highLevelDisplay = findViewById(R.id.highLevelDisplay);
        highLevelDisplay.setText("Highest level reached: " + currentHighLevel);
    }

    public void setTimeDisplay() {
        highTimeFile = getSharedPreferences(PREFERENCES_TIME, MODE_PRIVATE);
        currentTime = highTimeFile.getFloat("highestTime", 0);

        int minutes = (int)Math.floor(currentTime / 60);
        int seconds = (int)currentTime % 60;

        String minuteText;
        String secondText;

        if (minutes == 0) {
            minuteText = "";
        }
        else if (minutes == 1) {
            minuteText = Integer.toString(minutes) + " minute and ";
        }
        else {
            minuteText = Integer.toString(minutes) + " minutes and ";
        }

        if (seconds == 0) {
            secondText = "";
        }
        else if (seconds == 1) {
            secondText = Integer.toString(seconds) + " second";
        }
        else {
            secondText = Integer.toString(seconds) + " seconds";
        }

        timeDisplay = findViewById(R.id.highTimeDisplay);
        timeDisplay.setText("Longest time lasted: " + minuteText + secondText);
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