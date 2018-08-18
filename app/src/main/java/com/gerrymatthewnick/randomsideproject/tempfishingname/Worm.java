package com.gerrymatthewnick.randomsideproject.tempfishingname;

import android.content.Context;
import android.os.Handler;
import android.widget.RelativeLayout;

public class Worm extends Item {

    private final int ITEM_DELAY = 6000;
    private final String ITEM_TYPE = "worm";
    private final int ITEM_SIZE = 64;

    public Worm(RelativeLayout rl, Context con, Handler removeItemDelay) {
        super(rl, con, removeItemDelay);
    }

    public void spawnWorm() {
        super.spawn(ITEM_DELAY, ITEM_TYPE, ITEM_SIZE);
    }

}
