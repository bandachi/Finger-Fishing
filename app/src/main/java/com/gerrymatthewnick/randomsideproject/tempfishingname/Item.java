package com.gerrymatthewnick.randomsideproject.tempfishingname;

import android.content.Context;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.SPAWN_DELAY_CHERRY;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.SPAWN_DELAY_WORM;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.currentItemIdCherry;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.currentItemIdWorm;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.getScreenHeight;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.getScreenWidth;


public class Item {

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

        if (itemType.equals("cherry")) {
            item.setId(currentItemIdCherry);
            temp = SPAWN_DELAY_CHERRY;
        }
        else if (itemType.equals("worm")) {
            item.setId(currentItemIdWorm);
            temp = SPAWN_DELAY_WORM;
        }

        item.setImageResource(con.getResources().getIdentifier(itemType, "drawable", con.getPackageName()));
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        item.setLayoutParams(lp);
        int x = (int)Math.floor(Math.random() * (getScreenWidth() - item.getWidth()));
        int y = (int)Math.floor(Math.random() * (getScreenHeight()/2 - item.getHeight() * 2) + getScreenHeight()/2);
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
        }, temp);

    }
    public static void removeItem(ImageView item, RelativeLayout rl) {
        rl.removeView(item);
    }
}
