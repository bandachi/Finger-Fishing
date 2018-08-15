package com.gerrymatthewnick.randomsideproject.tempfishingname;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.PREFRENCES_HIGHSCORE;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.active;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.highscore;

public class LoseActivity extends AppCompatActivity {

    int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            score = extras.getInt("scoreNumber");
        }

        TextView scoreText = findViewById(R.id.scoreLoseDisplay);
        scoreText.setText(scoreText.getText() + Integer.toString(score));

        if (score > highscore) {
            SharedPreferences highscoreFile = getSharedPreferences(PREFRENCES_HIGHSCORE, MODE_PRIVATE);
            SharedPreferences.Editor editor = highscoreFile.edit();
            editor.putInt("highest", score);
            editor.apply();

            TextView loseText = findViewById(R.id.lose);
            loseText.setText("New Highscore!");
        }
    }

    public void onLose(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onLoseAgain(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        active = false;
    }
}
