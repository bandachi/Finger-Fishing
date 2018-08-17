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

    private final int ITEM_SIZE = 64;
    private RelativeLayout rl;
    private Context con;
    private Handler removeItemDelay;
    private String itemType;
    private ImageView item;

    public Item(RelativeLayout rl, Context con, Handler removeItemDelay, String itemType) {
        this.rl = rl;
        this.con = con;
        this.removeItemDelay = removeItemDelay;
        this.itemType = itemType;
    }

    public void spawn() {
        item = new ImageView(con);
        int temp = 4000;

        item.setImageResource(con.getResources().getIdentifier(itemType, "drawable", con.getPackageName()));
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        item.setLayoutParams(lp);
        int x = (int)Math.floor(Math.random() * (getScreenWidth() - ITEM_SIZE*2));
        int y = (int)Math.floor(Math.random() * (getScreenHeight()/2 - ITEM_SIZE*2)) + getScreenHeight()/2 - ITEM_SIZE;

        item.setX(x);
        item.setY(y);
        rl.addView(item);

        switch (itemType) {
            case "cherry":
                temp = SPAWN_DELAY_CHERRY;
                cherryImage = item;
                cherryExist = true;
                break;

            case "worm":
                temp = SPAWN_DELAY_WORM;
                wormImage = item;
                wormExist = true;
                break;

            case "coin":
                temp = SPAWN_DELAY_WORM;
                coinImage = item;
                coinExist = true;
                break;

            default:
                break;
        }

        removeItemDelay.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (item != null) {
                    rl.removeView(item);
                }
            }
        }, temp);
    }
    public static void removeItem(ImageView item, RelativeLayout rl) {
        rl.removeView(item);
    }
}