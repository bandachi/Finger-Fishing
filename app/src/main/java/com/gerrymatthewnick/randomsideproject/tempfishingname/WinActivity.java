package com.gerrymatthewnick.randomsideproject.tempfishingname;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.active;

public class WinActivity extends AppCompatActivity {

    int levelPass;
    int score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            levelPass = extras.getInt("levelNumber");
            score = extras.getInt("scoreNumber");
        }
    }
    public void onWin (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void onWinAgain (View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("levelNumber", levelPass);
        intent.putExtra("scoreNumber", score);
        startActivity(intent);
    }


    @Override
    public void onStart() {
        super.onStart();
        active = false;
    }
}
