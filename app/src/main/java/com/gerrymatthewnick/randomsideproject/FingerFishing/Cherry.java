package com.gerrymatthewnick.randomsideproject.FingerFishing;

import android.content.Context;
import android.os.Handler;
import android.widget.RelativeLayout;

public class Cherry extends Item {

    private final int ITEM_DELAY_MAX = 6000;
    private final int ITEM_DELAY_MIN = 2000;
    private final String ITEM_TYPE = "cherry";
    private final int ITEM_SIZE = 64;

    private int itemDelayRand;

    public Cherry(RelativeLayout rl, Context con, Handler removeItemDelay) {
        super(rl, con, removeItemDelay);
    }

    public void spawnCherry() {
        itemDelayRand = (int)(Math.random() * (ITEM_DELAY_MAX - ITEM_DELAY_MIN)) + ITEM_DELAY_MIN;
        super.spawn(itemDelayRand, ITEM_TYPE, ITEM_SIZE);
    }

    public int getItemDelay() {
        return itemDelayRand;
    }
}
