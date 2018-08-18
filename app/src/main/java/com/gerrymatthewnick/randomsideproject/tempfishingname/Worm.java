package com.gerrymatthewnick.randomsideproject.tempfishingname;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.wormExist;

public class Worm extends Item {
    public static int wormId;

    private final int ITEM_DELAY = 6000;
    private final String ITEM_TYPE = "worm";
    private final int ITEM_SIZE = 64;

    public Worm(RelativeLayout rl, Context con, Handler removeItemDelay) {
        super(rl, con, removeItemDelay);
    }

    public void spawnWorm() {
        wormId = View.generateViewId();
        super.spawn(ITEM_DELAY, ITEM_TYPE, ITEM_SIZE, wormId);
        wormExist = true;
    }

    public int getItemDelay() {
        return ITEM_DELAY;
    }
}
