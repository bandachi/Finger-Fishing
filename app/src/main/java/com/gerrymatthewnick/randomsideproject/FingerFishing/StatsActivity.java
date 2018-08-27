package com.gerrymatthewnick.randomsideproject.FingerFishing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.PREFERENCES_CHERRY_COUNT;
import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.PREFERENCES_COIN_COUNT;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        setCherryDisplay();
        setWormDisplay();
        setCoinDisplay();
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
