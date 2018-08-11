package com.gerrymatthewnick.randomsideproject.tempfishingname;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static com.gerrymatthewnick.randomsideproject.tempfishingname.gameActivity.active;

public class winActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
    }
    public void onWin (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void onWinAgain (View view) {
        Intent intent = new Intent(this, gameActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        active = false;
    }
}
