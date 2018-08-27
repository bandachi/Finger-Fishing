package com.gerrymatthewnick.randomsideproject.FingerFishing;

import android.content.Context;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.getScreenHeight;
import static com.gerrymatthewnick.randomsideproject.FingerFishing.GameActivity.getScreenWidth;


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

    protected ImageView getImage() {
        return item;
    }

    public void spawn(int itemDelay, String itemType, int itemSize, int id) {
        item = new ImageView(con);
        item.setId(id);

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
        }, itemDelay/4);
    }
    public static void removeItem(ImageView item, RelativeLayout rl) {
        rl.removeView(item);
    }
}
