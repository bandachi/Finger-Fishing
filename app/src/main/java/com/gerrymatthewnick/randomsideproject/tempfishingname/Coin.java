package com.gerrymatthewnick.randomsideproject.tempfishingname;

import android.content.Context;
import android.os.Handler;
import android.widget.RelativeLayout;

public class Coin extends Item {

    private final int ITEM_DELAY = 7000;
    private final String ITEM_TYPE = "coin";
    private final int ITEM_SIZE = 64;

    public Coin(RelativeLayout rl, Context con, Handler removeItemDelay) {
        super(rl, con, removeItemDelay);
    }

    public void spawnCoin() {
        super.spawn(ITEM_DELAY, ITEM_TYPE, ITEM_SIZE);
    }
}
