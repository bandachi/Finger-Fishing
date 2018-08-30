package com.gerrymatthewnick.randomsideproject.FingerFishing;

import android.content.Context;
import android.os.Handler;
import android.widget.RelativeLayout;

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
        itemDelayRand = (int)(Math.random() * (ITEM_DELAY_MAX - ITEM_DELAY_MIN)) + ITEM_DELAY_MIN;
        super.spawn(itemDelayRand, ITEM_TYPE, ITEM_SIZE);
    }

    public int getItemDelay() {
        return itemDelayRand;
    }
}
