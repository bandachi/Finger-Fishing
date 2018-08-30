package com.gerrymatthewnick.randomsideproject.FingerFishing;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.active;

public class WinActivity extends AppCompatActivity {

    int levelPass;
    int score;
    float time;
    private boolean delay = false;
    private AdView adview;
    Handler delayHandler = new Handler();

    public TextView winText;
    public TextView winScoreText;
    public TextView displayWinScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        //Load ads
        adview = findViewById(R.id.adViewWin);
        AdRequest adRequest = new AdRequest.Builder().build();
        adview.loadAd(adRequest);

        winText = findViewById(R.id.winTime);
        winScoreText = findViewById(R.id.winScreenScore);
        displayWinScore = findViewById(R.id.winScreenScoreText);

        //Get current level information
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            levelPass = extras.getInt("levelNumber");
            score = extras.getInt("scoreNumber");
            time = extras.getInt("currentTime");
            time = time/1000;
        }

        //Set TextViews with animations
        setWinScoreText();
        setDisplayWinScore();
        setWinText();

        delayHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Delay before user can switch activities
                delay = true;
            }
        }, 400);
    }
    public void setWinText() {
        winText.setText("Current time: " + Float.toString(time) + " seconds");
        winText.setVisibility(View.VISIBLE);
        winText.setAlpha(0.0f);
        winText.animate().alpha(1.0f).setListener(null).setDuration(2000);
    }

    public void setDisplayWinScore() {
        displayWinScore.setText(Integer.toString(score));
        displayWinScore.setVisibility(View.VISIBLE);
        displayWinScore.setAlpha(0.0f);
        displayWinScore.animate().alpha(1.0f).setListener(null).setDuration(1500);
    }

    public void setWinScoreText() {
        winScoreText.setVisibility(View.VISIBLE);
        winScoreText.setAlpha(0.0f);
        winScoreText.animate().alpha(1.0f).setListener(null).setDuration(1000);
    }

    public void onWinAgain (View view) {
        //Pass next level's information
        if (delay) {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("levelNumber", levelPass);
            intent.putExtra("scoreNumber", score);
            intent.putExtra("currentTime", time * 1000);
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
