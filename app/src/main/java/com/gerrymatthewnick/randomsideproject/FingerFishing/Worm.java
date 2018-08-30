package com.gerrymatthewnick.randomsideproject.FingerFishing;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import static android.content.Context.MODE_PRIVATE;
import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.PREFERENCES_WORM_COUNT;

public class Worm extends Item {

    private final int ITEM_DELAY_MAX = 8000;
    private final int ITEM_DELAY_MIN = 4000;
    private final String ITEM_TYPE = "worm";
    private final int ITEM_SIZE = 64;

    private int itemDelayRand;

    public Worm(RelativeLayout rl, Context con, Handler removeItemDelay) {
        super(rl, con, removeItemDelay);
    }

    public void spawnWorm() {
        itemDelayRand = (int)(Math.random() * (ITEM_DELAY_MAX - ITEM_DELAY_MIN)) + ITEM_DELAY_MIN;
        super.spawn(itemDelayRand, ITEM_TYPE, ITEM_SIZE);
    }

    public int getItemDelay() {
        return itemDelayRand;
    }

    public void wormEffect(Activity act, ProgressBar health, Sound sound) {
        Item.removeItem(this.getImage(), rl);

        health.incrementProgressBy(100);
        sound.playWormPickup();

        SharedPreferences wormCountFile = act.getSharedPreferences(PREFERENCES_WORM_COUNT, MODE_PRIVATE);
        int currentWormCount = wormCountFile.getInt("worms", 0);
        SharedPreferences.Editor editorStats = wormCountFile.edit();

        editorStats.putInt("worms", currentWormCount + 1);
        editorStats.apply();
    }
}
