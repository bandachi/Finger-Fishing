package com.gerrymatthewnick.randomsideproject.FingerFishing;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.cherryExist;

public class Cherry extends Item {
    public static int cherryId;

    private final int ITEM_DELAY = 4000;
    private final String ITEM_TYPE = "cherry";
    private final int ITEM_SIZE = 64;

    public Cherry(RelativeLayout rl, Context con, Handler removeItemDelay) {
        super(rl, con, removeItemDelay);
    }

    public void spawnCherry() {
        cherryId = View.generateViewId();
        super.spawn(ITEM_DELAY, ITEM_TYPE, ITEM_SIZE, cherryId);
        cherryExist = true;
    }

    public int getItemDelay() {
        return ITEM_DELAY;
    }
}
