package com.gerrymatthewnick.randomsideproject.tempfishingname;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.SPAWN_DELAY_CHERRY;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.SPAWN_DELAY_WORM;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.cherryExist;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.cherryImage;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.coinExist;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.coinImage;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.getScreenHeight;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.getScreenWidth;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.wormExist;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.wormImage;


public class Item {

    protected RelativeLayout rl;
    protected Context con;
    protected Handler removeItemDelay;
    protected ImageView item;

    public Item(RelativeLayout rl, Context con, Handler removeItemDelay) {
        this.rl = rl;
        this.con = con;
        this.removeItemDelay = removeItemDelay;
    }

    public void spawn(int itemDelay, String itemType, int itemSize) {
        item = new ImageView(con);


        item.setImageResource(con.getResources().getIdentifier(itemType, "drawable", con.getPackageName()));
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        item.setLayoutParams(lp);
        int x = (int)Math.floor(Math.random() * (getScreenWidth() - itemSize*2));
        int y = (int)Math.floor(Math.random() * (getScreenHeight()/2 - itemSize*2)) + getScreenHeight()/2 - itemSize;

        item.setX(x);
        item.setY(y);
        rl.addView(item);

        removeItemDelay.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (item != null) {
                    rl.removeView(item);
                }
            }
        }, itemDelay);
    }
    public static void removeItem(ImageView item, RelativeLayout rl) {
        rl.removeView(item);
    }
}