package com.gerrymatthewnick.randomsideproject.FingerFishing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.active;

public class MainActivity extends AppCompatActivity {

    private AdView adview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adview = findViewById(R.id.adViewMain);
        AdRequest adRequest = new AdRequest.Builder().build();
        adview.loadAd(adRequest);
    }

    //Change activity to GameActivity
    public void changeToGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void changeToSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    public void changeToInstructions(View view) {
        Intent intent = new Intent(this, InstructionActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    public void changeToShop(View view) {
        Intent intent = new Intent(this, ShopActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onStart() {
        super.onStart();
        active = false;
    }
    @Override
    public void onBackPressed() {
        //prevent back press
    }

}
//TODO different fish skins for coins