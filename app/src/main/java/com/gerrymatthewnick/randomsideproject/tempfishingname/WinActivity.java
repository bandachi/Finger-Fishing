package com.gerrymatthewnick.randomsideproject.tempfishingname;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.active;

public class WinActivity extends AppCompatActivity {

    int levelPass;
    int score;
    private boolean delay = false;
    Handler delayHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        TextView winText = findViewById(R.id.win);
        TextView winScoreText = findViewById(R.id.winScreenScore);
        TextView displayWinScore = findViewById(R.id.winScreenScoreText);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            levelPass = extras.getInt("levelNumber");
            score = extras.getInt("scoreNumber");
        }
        displayWinScore.setText(Integer.toString(score));

        winScoreText.setVisibility(View.VISIBLE);
        winScoreText.setAlpha(0.0f);
        winScoreText.animate().alpha(1.0f).setListener(null).setDuration(1000);

        displayWinScore.setVisibility(View.VISIBLE);
        displayWinScore.setAlpha(0.0f);
        displayWinScore.animate().alpha(1.0f).setListener(null).setDuration(1500);

        winText.setVisibility(View.VISIBLE);
        winText.setAlpha(0.0f);
        winText.animate().alpha(1.0f).setListener(null).setDuration(2000);

        delayHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                delay = true;
            }
        }, 400);
    }
    public void onWin (View view) {
        if (delay) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
    public void onWinAgain (View view) {
        if (delay) {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("levelNumber", levelPass);
            intent.putExtra("scoreNumber", score);
            startActivity(intent);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        active = false;
    }
}
