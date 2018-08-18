package com.gerrymatthewnick.randomsideproject.tempfishingname;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.coinExist;

public class Coin extends Item {
    public static int coinId;

    private final int ITEM_DELAY = 7000;
    private final String ITEM_TYPE = "coin";
    private final int ITEM_SIZE = 64;

    public Coin(RelativeLayout rl, Context con, Handler removeItemDelay) {
        super(rl, con, removeItemDelay);
    }

    public void spawnCoin() {
        coinId = View.generateViewId();
        super.spawn(ITEM_DELAY, ITEM_TYPE, ITEM_SIZE, coinId);
        coinExist = true;
    }

    public int getItemDelay() {
        return ITEM_DELAY;
    }
}
