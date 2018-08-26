package com.gerrymatthewnick.randomsideproject.FingerFishing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.PREFERENCES_HIGHSCORE;
import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.active;
import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.getScreenHeight;
import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.getScreenWidth;

public class LoseActivity extends AppCompatActivity {

    int score;
    private boolean delay = false;
    private AdView adview;
    private Context con = this;
    Handler handlerDelay = new Handler();
    Handler changeFishVelocity;
    Handler moveFish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose);

        TextView loseText = findViewById(R.id.lose);
        TextView scoreText = findViewById(R.id.scoreLoseDisplay);

        adview = findViewById(R.id.adViewLose);
        AdRequest adRequest = new AdRequest.Builder().build();
        adview.loadAd(adRequest);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            score = extras.getInt("scoreNumber");
        }

        scoreText.setText(scoreText.getText() + Integer.toString(score));

        scoreText.setVisibility(View.VISIBLE);
        scoreText.setAlpha(0.0f);
        scoreText.animate().alpha(1.0f).setListener(null).setDuration(1500);

        loseText.setVisibility(View.VISIBLE);
        loseText.setAlpha(0.0f);
        loseText.animate().alpha(1.0f).setListener(null).setDuration(2000);

        SharedPreferences settingsHigh = getSharedPreferences(PREFERENCES_HIGHSCORE, MODE_PRIVATE);
        int highscore = settingsHigh.getInt("highest", 0);

        if (score > highscore) {
            SharedPreferences highscoreFile = getSharedPreferences(PREFERENCES_HIGHSCORE, MODE_PRIVATE);
            SharedPreferences.Editor editor = highscoreFile.edit();
            editor.putInt("highest", score);
            editor.apply();
            loseText.setText("New Highscore!");
        }

        changeFishVelocity = new Handler();
        moveFish = new Handler();

        handlerDelay.postDelayed(new Runnable() {
            @Override
            public void run() {
                delay = true;
                int fishId = getResources().getIdentifier("fish1" , "drawable", getPackageName());
                RelativeLayout rl = findViewById(R.id.rlLose);

                Fish fish = new Fish(fishId, 1000, 10, rl, con, View.generateViewId(), changeFishVelocity, moveFish, 2);
                fish.spawnFish();
                fish.setX(getScreenWidth()/2);
                fish.setY(getScreenHeight()/2);

                fish.startChangeVelocity();
                fish.startVelocity();
            }
        }, 400);
    }

    public void onLose(View view) {
        if (delay) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    public void onLoseAgain(View view) {
        if (delay) {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        active = false;
    }

    @Override
    public void onBackPressed() {
        //prevent back button
    }
}