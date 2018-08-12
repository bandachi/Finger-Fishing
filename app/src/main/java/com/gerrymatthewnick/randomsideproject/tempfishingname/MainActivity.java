package com.gerrymatthewnick.randomsideproject.tempfishingname;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.active;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Change activity to GameActivity
    public void changeToGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void changeToSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
    public void instructionButton (View view) {
        Intent intent = new Intent(this, InstructionActivity.class);
        startActivity(intent);
    }
    @Override
    public void onStart() {
        super.onStart();
        active = false;
    }

}
