package com.gerrymatthewnick.randomsideproject.FingerFishing;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.cherryExist;

public class Cherry extends Item {
    public static int cherryId;

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
        cherryId = View.generateViewId();
        super.spawn(itemDelayRand, ITEM_TYPE, ITEM_SIZE, cherryId);
        cherryExist = true;
    }

    public int getItemDelay() {
        return itemDelayRand;
    }
}
