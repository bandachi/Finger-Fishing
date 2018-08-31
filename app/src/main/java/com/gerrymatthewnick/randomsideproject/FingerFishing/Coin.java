package com.gerrymatthewnick.randomsideproject.FingerFishing;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;
import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.PREFERENCES_COINS;
import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.PREFERENCES_COIN_COUNT;

public class Coin extends Item {

    private final int ITEM_DELAY_MAX = 10000;
    private final int ITEM_DELAY_MIN = 4000;
    private final String ITEM_TYPE = "coin";
    private final int ITEM_SIZE = 64;

    private int itemDelayRand;

    public Coin(RelativeLayout rl, Context con, Handler removeItemDelay) {
        super(rl, con, removeItemDelay);
    }

    public void spawnCoin() {
        //Set item delay at random, and spawn with that item delay
        itemDelayRand = (int) (Math.random() * (ITEM_DELAY_MAX - ITEM_DELAY_MIN)) + ITEM_DELAY_MIN;
        super.spawn(itemDelayRand, ITEM_TYPE, ITEM_SIZE);
    }

    public int getItemDelay() {
        return itemDelayRand;
    }

    public void coinEffect(Activity act) {
        SharedPreferences settingsCoin = act.getSharedPreferences(PREFERENCES_COINS, MODE_PRIVATE);
        int coins = settingsCoin.getInt("coinCount", 0);

        Item.removeItem(this.getImage(), rl);

        //for current coin count
        coins++;
        SharedPreferences coinsFile = act.getSharedPreferences(PREFERENCES_COINS, MODE_PRIVATE);
        SharedPreferences.Editor editor = coinsFile.edit();
        editor.putInt("coinCount", coins);
        editor.apply();

        TextView coin = act.findViewById(R.id.coinDisplay);
        coin.setText(Integer.toString(coins));

        //for total coin count over the total time played
        SharedPreferences coinCountFile = act.getSharedPreferences(PREFERENCES_COIN_COUNT, MODE_PRIVATE);
        int currentCoinCount = coinCountFile.getInt("coins", 0);
        SharedPreferences.Editor editorStats = coinCountFile.edit();

        editorStats.putInt("coins", currentCoinCount + 1);
        editorStats.apply();
    }
}
