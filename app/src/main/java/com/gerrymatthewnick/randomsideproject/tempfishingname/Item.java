package com.gerrymatthewnick.randomsideproject.tempfishingname;

import android.content.Context;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.SPAWN_DELAY;
import static com.gerrymatthewnick.randomsideproject.tempfishingname.GameActivity.currentItemId;
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
        item.setId(currentItemId);
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
        }, SPAWN_DELAY);

    }
    public static void removeItem(ImageView item, RelativeLayout rl) {
        rl.removeView(item);
    }
}
