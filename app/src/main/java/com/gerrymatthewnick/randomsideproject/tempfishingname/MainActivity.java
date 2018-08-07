package com.gerrymatthewnick.randomsideproject.tempfishingname;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Change activity to gameActivity
    public void changeToGame(View view) {
        Intent intent = new Intent(this, gameActivity.class);
        startActivity(intent);
    }

    public void changeToSettings(View view) {
        Intent intent = new Intent(this, settingsActivity.class);
        startActivity(intent);
    }


}
