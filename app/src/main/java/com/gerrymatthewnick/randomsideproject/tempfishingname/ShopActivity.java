package com.gerrymatthewnick.randomsideproject.tempfishingname;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShopActivity extends AppCompatActivity {

    public int coinsShop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        SharedPreferences settingsCoins = getSharedPreferences("coins", MODE_PRIVATE);
        coinsShop = settingsCoins.getInt("coinCount", 0);

        TextView coinDisplay = findViewById(R.id.coinDisplayShop);
        coinDisplay.setText(coinDisplay.getText().toString() + Integer.toString(coinsShop));
        //all yours coinsShop is the amount of coins user has
    }
}
