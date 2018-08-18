package com.gerrymatthewnick.randomsideproject.tempfishingname;

import android.content.Context;
import android.os.Handler;
import android.widget.RelativeLayout;

public class Cherry extends Item {
    private final int ITEM_DELAY = 4000;
    private final String ITEM_TYPE = "cherry";
    private final int ITEM_SIZE = 64;

    public Cherry(RelativeLayout rl, Context con, Handler removeItemDelay) {
        super(rl, con, removeItemDelay);
    }

    public void spawnCherry() {
        super.spawn(ITEM_DELAY, ITEM_TYPE, ITEM_SIZE);
    }
}
