package com.gerrymatthewnick.randomsideproject.FingerFishing;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.wormExist;

public class Worm extends Item {
    public static int wormId;

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
        wormId = View.generateViewId();
        super.spawn(itemDelayRand, ITEM_TYPE, ITEM_SIZE, wormId);
        wormExist = true;
    }

    public int getItemDelay() {
        return itemDelayRand;
    }
}
